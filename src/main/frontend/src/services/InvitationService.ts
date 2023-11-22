import { find } from 'lodash';
import GuestService from "./GuestService";
import type { Guest, NewGuest } from "./GuestService";
import HttpClient, {baseURL} from "./Axios";
import _ from 'lodash';
export interface InvitationCollection {
    class: string[];
    entities: InvitationEntity[];
    links: Link[];
    actions: Action[];
    title: string;
}

export interface Action {
    name: string;
    method: string;
    href: string;
    title: string;
    type?: string;
    fields?: Field[];
}

export interface Field {
    name: string;
    type: Type;
    title: FieldTitle;
}

export enum FieldTitle {
    AField = "A field",
}

export enum Type {
    Number = "number",
    Text = "text",
}

export interface InvitationEntity {
    class: string[];
    rel: string[];
    properties: InvitationProperties;
    entities: InvitationEntityEntities[];
    links: Link[];
    actions: Action[];
    title: string;
}

export interface InvitationEntityEntities {
    class: string[];
    rel: string[];
    entities: GuestStatusEntity[];
    title: string;
}

export interface GuestStatusEntity {
    class: string[];
    rel: string[];
    entities?: GuestStatusEntity[];
    links: Link[];
    title: string;
    properties: GuestStatusProperties;
}

export interface Link {
    rel: Rel[];
    href: string;
    title: LinkTitle;
}

export enum Rel {
    Guests = "guests",
    Invitations = "invitations",
    Self = "self",
}

export enum LinkTitle {
    ALink = "A link",
    TheSelfLink = "The self link",
}

export interface GuestStatusProperties {
    guestId: string;
    accepted: boolean;
}

export interface InvitationProperties {
    id: string;
    code: string;
    name: string;
    specialRequest: string;
    specialRequestAccepted: boolean;
    specialRequestAnswer: string;
    status: string;
    assignedGuestsCount: number;
    confirmedGuestsCount: number;
}

export interface Invitation {
    id: string;
    code: string;
    name: string;
    specialRequest: string;
    specialRequestAccepted: boolean;
    specialRequestAnswer: string;
    status: string;
    assignedGuestsCount: number;
    confirmedGuestsCount: number;
    actions: {
        edit?: string;
        delete?: string;
    },
    guests: GuestStatus[];
}

export interface GuestStatus {
    guestId: string;
    accepted: boolean;
}

export interface NewInvitation {
    name: string;
    specialRequest: string;
}

export interface UpdateInvitation {
    id: string;
    specialRequestAccepted: boolean;
    specialRequestAnswer: string;
}

class InvitationService {
    public async getInvitations(): Promise<Invitation[]> {
        const response = await HttpClient.get<InvitationCollection>("/api/invitations/");
        const result: Invitation[] = []
        response.data.entities?.forEach(e => {
            result.push(this.mapToInvitation(e));
        });
        return result;
    }
    private mapToInvitation(e: InvitationEntity) {
        const deleteAction = find(e.actions, { method: "DELETE" })?.href;
        const patchAction = find(e.actions, { method: "PATCH" })?.href;
        const guests: GuestStatus[] = _.map(e.entities[0].entities[0].entities, x => {
            const y: GuestStatus = {
                guestId: x.properties?.guestId,
                accepted: x.properties?.accepted
            };
            return y;
        });
        const myGuest: Invitation = {
            id: e.properties.id,
            code: e.properties.code,
            name: e.properties.name,
            specialRequest: e.properties.specialRequest,
            specialRequestAccepted: e.properties.specialRequestAccepted,
            specialRequestAnswer: e.properties.specialRequestAnswer,
            status: e.properties.status,
            assignedGuestsCount: e.properties.assignedGuestsCount,
            confirmedGuestsCount: e.properties.confirmedGuestsCount,
            actions: {
                edit: patchAction?.replace(baseURL, ""),
                delete: deleteAction?.replace(baseURL, "")
            },
            guests: guests
        };
        return myGuest;
    }

    public async getInvitation(id: string): Promise<Invitation> {
        const response = await HttpClient.get<InvitationEntity>("/api/invitations/" + id);
        const e = response.data;
        return this.mapToInvitation(e);
    }
    public async deletInvitation(action: string): Promise<void> {
        await HttpClient.delete<void>(action);
    }
    public async updateGuestStatus(invitatuionId: string, status: GuestStatus): Promise<void> {
        await HttpClient.patch<void>("/api/invitations/" + invitatuionId + "/guests/" + status.guestId, status);
    }
    public async updateInvitation(invitation: UpdateInvitation): Promise<void> {
        await HttpClient.patch<void>("/api/invitations/" + invitation.id, invitation);
    }
    public async createInvitation(invitation: NewInvitation, guests: Guest[], companionCount: number, childrenCount: number): Promise<void> {
        const newInvitation = await HttpClient.post<InvitationEntity>("/api/invitations/", invitation);
        const newId = newInvitation.data.properties.id;
        console.log("newId=" + newId);
        for (const guest of guests) {
            await HttpClient.post<void>("/api/invitations/" + newId + "/guests", guest.id, { headers: { 'Content-Type': 'text/plain' } });
        }
        for (let index = 0; index < companionCount; index++) {
            const ng: NewGuest = {
                firstname: "",
                lastname: "",
                guestType: "Companion"
            };
            const guest = await GuestService.createGuest(ng);
            await HttpClient.post<void>("/api/invitations/" + newId + "/guests", guest.id, { headers: { 'Content-Type': 'text/plain' } });
        }
        for (let index = 0; index < childrenCount; index++) {
            const ng: NewGuest = {
                firstname: "",
                lastname: "",
                guestType: "Child"
            };
            const guest = await GuestService.createGuest(ng);
            await HttpClient.post<void>("/api/invitations/" + newId + "/guests", guest.id, { headers: { 'Content-Type': 'text/plain' } });
        }
    }
}

export default new InvitationService();