export type TrialStatus = 'DRAFT' | 'ONGOING' | 'COMPLETED'

export interface Trial {
    id: number
    name: string
    location: string | null
    status: TrialStatus
}
export interface TrialRequest {
    name: string
    location?: string | null
    status: TrialStatus
}