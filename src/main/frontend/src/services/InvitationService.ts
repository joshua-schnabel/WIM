import axios from "axios";
import { find } from 'lodash';
import GuestService from "./GuestService";
import type { Guest, NewGuest } from "./GuestService";

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
    properties?: GuestStatusProperties;
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
    specialRequestAccepted: string;
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
    specialRequestAccepted: string;
    specialRequestAnswer: string;
    status: string;
    assignedGuestsCount: number;
    confirmedGuestsCount: number;
    actions: {
        edit?: string;
        delete?: string;
    }
}

export interface NewInvitation {
    name: string;
    specialRequest: string;
}

class InvitationService {
    public async getInvitations(): Promise<Invitation[]> {
        const response = await axios.get<InvitationCollection>("/api/invitations/");
        const result: Invitation[] = []
        response.data.entities?.forEach(e => {
            const deleteAction = find(e.actions, { method: "DELETE" })?.href;
            const patchAction = find(e.actions, { method: "PATCH" })?.href;
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
                    edit: patchAction,
                    delete: deleteAction
                }
            };
            result.push(myGuest);
        });
        return result;
    }
    public async deletInvitation(action: string): Promise<void> {
        await axios.delete<void>(action);
    }
    public async createInvitation(invitation: NewInvitation, guests: Guest[], companionCount: number, childrenCount: number): Promise<void> {
        const newInvitation = await axios.post<InvitationEntity>("/api/invitations/", invitation);
        const newId = newInvitation.data.properties.id;
        console.log("newId=" + newId);
        for (const guest of guests) {
            await axios.post<void>("/api/invitations/" + newId + "/guests", guest.id, { headers: { 'Content-Type': 'text/plain' } });
        }
        for (let index = 0; index < companionCount; index++) {
            const ng: NewGuest = {
                firstname: "",
                lastname: "",
                guestType: "Companion"
            };
            const guest = await GuestService.createGuest(ng);
            await axios.post<void>("/api/invitations/" + newId + "/guests", guest.id, { headers: { 'Content-Type': 'text/plain' } });
        }
        for (let index = 0; index < childrenCount; index++) {
            const ng: NewGuest = {
                firstname: "",
                lastname: "",
                guestType: "Child"
            };
            const guest = await GuestService.createGuest(ng);
            await axios.post<void>("/api/invitations/" + newId + "/guests", guest.id, { headers: { 'Content-Type': 'text/plain' } });
        }
    }
}

export default new InvitationService();