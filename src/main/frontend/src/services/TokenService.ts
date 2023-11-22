import HttpClient, { APIError } from "./Axios";
import { decodeJwt } from "jose";

class TokenService {
    public async removeToken() {
        localStorage.removeItem("token");
    }
    public async getToken(code: string): Promise<boolean> {
        await this.removeToken();
        try {
            const response = await HttpClient.post<string>("/api/auth/code/", code, { headers: { 'Content-Type': 'text/plain' } });
            if (response.status === 200) {
                localStorage.setItem("token", response.data);
            }
            return response.status === 200;
        } catch (err) {
            if (err instanceof APIError) {
                return false;
            }
            else throw err;
        }
    }
    public async getLocalToken(): Promise<string> {
        const value = localStorage.getItem("token");
        if (value !== null)
            return value;
        throw new Error("No token found");
    }
    public async localTokenSet(): Promise<boolean> {
        return (localStorage.getItem("token") != null);
    }
    public async getInvitationId(): Promise<string> {
        const decoded = decodeJwt(await this.getLocalToken());
        return <string>decoded.invitationId;
    }
}

export default new TokenService();