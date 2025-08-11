window.onload = function() {
    window.ui = SwaggerUIBundle({
        url: "https://taskmanager-hidden-sky-719.fly.dev/v3/api-docs",
        dom_id: '#swagger-ui',
        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],
        plugins: [
            SwaggerUIBundle.plugins.DownloadUrl
        ],
        layout: "StandaloneLayout",
        deepLinking: true,
        validatorUrl: null,
        requestInterceptor: (req) => {
            if (req.url.startsWith('/')) {
                req.url = 'https://taskmanager-hidden-sky-719.fly.dev' + req.url;
            }
            const token = localStorage.getItem('swagger_token');
            if (token) {
                req.headers.Authorization = 'Bearer ' + token;
            }
            return req;
        }
    });
};