import axios from "axios";
import { find } from 'lodash';

export interface NewGuest {
    firstname: string;
    lastname: string;
    guestType: string;
}

export interface Guest {
    id: string;
    firstname: string;
    lastname: string;
    guestType: string;
    assigned: boolean;
    actions: {
        edit?: string;
        delete?: string;
    }
}

export interface GuestsCollection {
    class?: (string)[] | null;
    entities?: (GuestEntitiesEntity)[] | null;
    links?: (LinksEntity)[] | null;
    actions?: (ActionsEntity)[] | null;
    title: string;
}
export interface GuestEntitiesEntity {
    class?: (string)[] | null;
    rel?: (string)[] | null;
    properties: Properties;
    links?: (LinksEntity)[] | null;
    actions?: (ActionsEntity1)[] | null;
    title: string;
}
export interface Properties {
    id: string;
    firstname: string;
    lastname: string;
    guestType: string;
    assigned: boolean;
}
export interface LinksEntity {
    rel?: (string)[] | null;
    href: string;
    title: string;
}
export interface ActionsEntity1 {
    name: string;
    method: string;
    href: string;
    title: string;
}
export interface ActionsEntity {
    name: string;
    method: string;
    href: string;
    title: string;
    type: string;
    fields?: (FieldsEntity)[] | null;
}
export interface FieldsEntity {
    name: string;
    type: string;
    title: string;
}


class GuestService {
    public async getGuests(): Promise<Guest[]> {
        const response = await axios.get<GuestsCollection>("/api/guests/");
        const result: Guest[] = []
        response.data.entities?.forEach(e => {
            const myGuest: Guest = this.toGuest(e);
            result.push(myGuest);
        });
        return result;
    }

    private toGuest(e: GuestEntitiesEntity): Guest {
        const deleteAction = find(e.actions, { method: "DELETE" })?.href;
        const patchAction = find(e.actions, { method: "PATCH" })?.href;
        const myGuest: Guest = {
            id: e.properties.id,
            firstname: e.properties.firstname,
            lastname: e.properties.lastname,
            guestType: e.properties.guestType,
            assigned: e.properties.assigned,
            actions: {
                edit: patchAction,
                delete: deleteAction
            }
        };
        return myGuest;
    }

    public async deleteGuest(action: string): Promise<void> {
        await axios.delete<void>(action);
    }

    public async createGuest(guest: NewGuest): Promise<Guest> {
        const response = await axios.post<GuestEntitiesEntity>("/api/guests/", guest);
        return this.toGuest(response.data);
    }
}

export default new GuestService();