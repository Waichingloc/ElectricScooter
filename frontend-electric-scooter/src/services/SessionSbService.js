export class SessionSbService {
    RESOURCES_URL;              // the back-end base url for authentication resources
    BROWSER_STORAGE_ITEM_NAME;  // the key into browser storage for retaining the token and account
    _currentToken;              // the current authentication token of this session
                                // to be injected in the authorization header of every outgoing request
    _currentAccount;            // the account instant of the currently logged on user


    constructor(RESOURCES_URL, BROWSER_STORAGE_ITEM_NAME) {
        this.RESOURCES_URL = RESOURCES_URL;
        this.BROWSER_STORAGE_ITEM_NAME = BROWSER_STORAGE_ITEM_NAME;
        this._currentToken = null;
        this._currentAccount = null;
        this.getTokenFromBrowserStorage();
        console.log("SessionService has recovered token: ", this._currentToken);
    }

    getTokenFromBrowserStorage() {
        console.log("Attempting to recover token from browser storage...")
        if (this._currentToken != null) return this._currentToken
        this._currentToken = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        let userAccount = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");

        if (this._currentToken == null) {
            try {
                if (localStorage !== null) {
                    this._currentToken = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
                    userAccount = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");
                    window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, this._currentToken);
                    window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC", userAccount);
                }
            } catch (e) {
                console.log("SessionStorage is not available, using LocalStorage instead.");
            }
        }
        if (userAccount != null) {
            this._currentAccount = JSON.parse(userAccount);
        }
        return this._currentToken;
    }

    saveTokenIntoBrowserStorage(token, user) {
        this._currentToken = token;
        this._currentAccount = user;
        try {
            if (token == null) {
                this._currentAccount = null;
                window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
                window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");

                if (localStorage !== sessionStorage) {
                    window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
                    window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC");
                }
            } else {
                console.log("New token for " + user.name + ": " + token);
                // insert into sessionStorage
                window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
                window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC", JSON.stringify(user));

                // insert into localStorage
                window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
                window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME + "_ACC", JSON.stringify(user));
            }
        }
        catch (e) {
            console.log("SessionStorage is not available, using LocalStorage instead.");
        }
    }

    getUserName() {
        return this._currentAccount ? this._currentAccount.name : '';

    }

    isAuthenticated() {
        return this._currentAccount != null;
    }

    async asyncSignIn(email, password) {
        try {
            const body = JSON.stringify({email: email, password: password});
            let response = await fetch(this.RESOURCES_URL + "/login", {
                method: 'POST', headers: {
                    'Content-Type': 'application/json',
                }, body: body,
            });
            console.log(response);
            if (response.ok) {
                let token = await response.json();
                this.saveTokenIntoBrowserStorage(response.headers.get('Authorization'), token);
                return true;
            } else {
                console.log(response, !response.bodyUsed ? await response.text() : "");
                if (response.status === 401) {
                    console.error("Unauthorized. Token may have expired or invalid.");
                } else {
                    console.error("Error in response: ", response);
                }
                return false;
            }
        } catch (e) {
            console.error("Error in asyncSignIn(): ", e);
            return false;
        }
    }

    signOut() {
        this.saveTokenIntoBrowserStorage(null, null);
    }


    get currentToken() {
        return this._currentToken;
    }

    get currentAccount() {
        return this._currentAccount;
    }
}
