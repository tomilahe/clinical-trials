import { useCallback, useEffect, useState } from 'react'
import type { Trial, TrialRequest, TrialStatus } from './types'
import { createTrial, fetchTrials, updateTrial } from './api'
import './App.css'

const STATUSES: TrialStatus[] = ['DRAFT', 'ONGOING', 'COMPLETED']

type FormState = { name: string; location: string; status: TrialStatus }
type FormErrors = Partial<Record<keyof FormState, string>>

function validate(form: FormState): FormErrors {
    const errors: FormErrors = {}

    const name = form.name.trim()
    console.log('name length:', name.length)

    if (!name) errors.name = 'Name is required'
    else if (name.length < 10 || name.length > 100) errors.name = 'Name must be 10-100 characters'

    const loc = form.location.trim()
    if (loc && loc.length > 200) errors.location = 'Location must be max 200 characters'

    if (!form.status) errors.status = 'Status is required'

    return errors
}

export default function App() {
    const [trials, setTrials] = useState<Trial[]>([])
    const [loading, setLoading] = useState(true)
    const [apiError, setApiError] = useState('')

    const [editingId, setEditingId] = useState<number | null>(null)
    const [form, setForm] = useState<FormState>({ name: '', location: '', status: 'DRAFT' })
    const [errors, setErrors] = useState<FormErrors>({})

    const isEditing = editingId !== null

    const refresh = useCallback(async () => {
        setLoading(true)
        setApiError('')
        try {
            const data = await fetchTrials()
            setTrials(data)
        } catch (e: any) {
            setApiError(e?.message ?? 'Failed to load trials')
        } finally {
            setLoading(false)
        }
    }, [])

    useEffect(() => {
        refresh()
    }, [refresh])

    const startEdit = useCallback((t: Trial) => {
        setEditingId(t.id)
        setForm({
            name: t.name ?? '',
            location: t.location ?? '',
            status: t.status,
        })
        setErrors({})
        setApiError('')
        window.scrollTo({ top: 0, behavior: 'smooth' })
    }, [])

    const resetForm = useCallback(() => {
        setEditingId(null)
        setForm({ name: '', location: '', status: 'DRAFT' })
        setErrors({})
        setApiError('')
    }, [])

    const onSubmit = useCallback(
        async (e: React.FormEvent) => {
            e.preventDefault()
            setApiError('')

            const v = validate(form)
            setErrors(v)
            if (Object.keys(v).length) return

            const name = form.name.trim()
            const locationTrimmed = form.location.trim()

            const body: TrialRequest = {
                name,
                location: locationTrimmed === '' ? null : locationTrimmed,
                status: form.status,
            }

            try {
                if (isEditing) {
                    if (editingId === null) return // extra safety
                    await updateTrial(editingId, body)
                } else {
                    await createTrial(body)
                }

                await refresh()
                resetForm()
            } catch (err: any) {
                setApiError(err?.message ?? 'Request failed')
            }
        },
        [form, isEditing, editingId, refresh, resetForm],
    )

    return (
        <div className="page">
            <div className="container">
                <header className="header">
                    <div>
                        <h1 className="title">Clinical Trial Management</h1>
                        <p className="subtitle">Create, update, and track trials in one place.</p>
                    </div>
                </header>

                <div className="grid">
                    <section className="card">
                        <div className="cardHeader">
                            <div>
                                <h2 className="cardTitle">{isEditing ? `Update Trial #${editingId}` : 'Add Trial'}</h2>
                                <p className="cardSubtitle">
                                    {isEditing ? 'Edit the fields and save your changes.' : 'Fill out the form to create a new trial.'}
                                </p>
                            </div>

                            {isEditing && (
                                <button className="btn btnGhost" type="button" onClick={resetForm}>
                                    Cancel
                                </button>
                            )}
                        </div>

                        {apiError && <div className="alert">{apiError}</div>}

                        <form onSubmit={onSubmit} className="form">
                            <div className="field">
                                <label className="label">
                                    Name <span className="req">*</span>
                                </label>
                                <input
                                    className={`input ${errors.name ? 'inputError' : ''}`}
                                    value={form.name}
                                    onChange={(e) => setForm((p) => ({ ...p, name: e.target.value }))}
                                    placeholder="10–100 characters"
                                    maxLength={100}
                                    aria-invalid={!!errors.name}
                                />
                                {errors.name && <div className="fieldError">{errors.name}</div>}
                            </div>

                            <div className="field">
                                <label className="label">Location</label>
                                <input
                                    className={`input ${errors.location ? 'inputError' : ''}`}
                                    value={form.location}
                                    onChange={(e) => setForm((p) => ({ ...p, location: e.target.value }))}
                                    placeholder="Optional (max 200 chars)"
                                    maxLength={200}
                                    aria-invalid={!!errors.location}
                                />
                                {errors.location && <div className="fieldError">{errors.location}</div>}
                            </div>

                            <div className="field">
                                <label className="label">
                                    Status <span className="req">*</span>
                                </label>
                                <select
                                    className="select"
                                    value={form.status}
                                    onChange={(e) => setForm((p) => ({ ...p, status: e.target.value as TrialStatus }))}
                                >
                                    {STATUSES.map((s) => (
                                        <option key={s} value={s}>
                                            {s}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="actions">
                                <button className="btn btnPrimary" type="submit">
                                    {isEditing ? 'Save changes' : 'Create trial'}
                                </button>
                                <button className="btn btnSoft" type="button" onClick={resetForm}>
                                    Reset
                                </button>
                            </div>
                        </form>
                    </section>

                    <section className="card">
                        <div className="cardHeader">
                            <div>
                                <h2 className="cardTitle">Trials</h2>
                                <p className="cardSubtitle">All trials currently in the system.</p>
                            </div>
                        </div>

                        {loading ? (
                            <div className="muted">Loading…</div>
                        ) : trials.length === 0 ? (
                            <div className="empty">
                                <div className="emptyTitle">No trials yet</div>
                                <div className="emptyText">Create your first trial using the form.</div>
                            </div>
                        ) : (
                            <div className="tableWrap">
                                <table className="table">
                                    <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Location</th>
                                        <th>Status</th>
                                        <th className="tableActions" />
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {trials.map((t) => (
                                        <tr key={t.id}>
                                            <td className="monoClamp" title={t.name ?? ''}>
                                                {t.name}
                                            </td>
                                            <td title={t.location ?? ''}>{t.location ?? ''}</td>
                                            <td>
                                                <span className={`pill pill-${t.status.toLowerCase()}`}>{t.status}</span>
                                            </td>
                                            <td className="tableActions">
                                                <button className="btn btnGhost" type="button" onClick={() => startEdit(t)}>
                                                    Edit
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        )}
                    </section>
                </div>
            </div>
        </div>
    )
}
