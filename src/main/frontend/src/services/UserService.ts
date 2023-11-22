class UserService {
    public async isUserLoggedIn(): Promise<boolean> {
        return localStorage.getItem("isLoggedIn") == "true" || false;
    }
    public async setUserLoggedIn(value: boolean): Promise<void> {
        localStorage.setItem("isLoggedIn", value ? "true" : "false")
    }
}

export default new UserService();