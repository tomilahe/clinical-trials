import type {Trial, TrialRequest} from './types'

export async function fetchTrials(): Promise<Trial[]> {
    const response = await fetch('/api/trials')
    if (!response.ok) {
        throw new Error('Failed to fetch trials')
    }
    return response.json()
}


export async function createTrial(body: TrialRequest): Promise<Trial> {
    const res = await fetch('/api/trials', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
    })
    if (!res.ok) {
        const err = await res.json().catch(() => null)
        throw new Error(err?.message ?? `Create failed (${res.status})`)
    }
    return res.json()
}

export async function updateTrial(id: number, body: TrialRequest): Promise<Trial> {
    const res = await fetch(`/api/trials/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
    })
    if (!res.ok) {
        const err = await res.json().catch(() => null)
        throw new Error(err?.message ?? `Update failed (${res.status})`)
    }
    return res.json()
}
