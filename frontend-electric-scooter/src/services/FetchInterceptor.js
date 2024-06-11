import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static theInstance;
    sessionService;
    router;
    unregister;

    constructor(sessionService, router) {
        FetchInterceptor.theInstance = this; // because fetchIntercept hooks use old school ‘this’...
        this.sessionService = sessionService;
        this.router = router;
        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register(this);

        // console.log("FetchInterceptor has been registered; current token = ", FetchInterceptor.theInstance.sessionService.currentToken);
    }

    request(url, options) {
        let token = FetchInterceptor.theInstance.sessionService.currentToken;
        console.log("FetchInterceptor request: ", url, options, token);
        if (token == null) {
            return [url, options];
        } else if (options == null) {
            return [url, {headers: {Authorization: token}}]
        } else {
            let newOptions = {...options};
            newOptions.headers = {
                ...options.headers,
                Authorization: `Bearer ${token}`
            };
            return [url, newOptions];
        }
    }

    requestError(error) {
        return Promise.reject(error);
    }

    responseError(error) {
        // return Promise.reject(error);
        return console.log("FetchInterceptor responseError: ", error)
    }

    response(response) {
        // console.log("FetchInterceptor response: ", response);
        try {
            // FetchInterceptor.theInstance.tryRecoverNewJWToken(response);
            if (response.status >= 400 && response.status < 600) {
                FetchInterceptor.theInstance.handleErrorInResponse(response);
            }
            return response;
        } catch (e) {
            console.error("FetchInterceptor response: ", e);
        }
    }

    tryRecoverNewJWToken(response) {
        console.log(response, response.headers)
        try {
            if (response != null && response.headers != null) {
                let newToken = response.headers.get("Authorization");
                // let newUser = await response.json();
                console.log("FetchInterceptor tryRecoverNewJWToken: ", newToken);
                // console.log(newUser);
            }
        } catch (e) {
            console.error("FetchInterceptor tryRecoverNewJWToken: ", e);
        }
    }

    handleErrorInResponse(response) {
        try {
            if (response.status == 401) {
                console.log("Unauthorized. Token may have expired or invalid.");
            } else {
                console.log("Unexpexted error during login.");
            }
            this.responseError(response);
            this.router.push({path: '/sign-out',});
        } catch (e) {
            console.error("FetchInterceptor handleErrorInResponse: ", e);
        }
    }
}
