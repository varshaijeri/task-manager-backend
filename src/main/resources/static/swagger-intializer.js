window.onload = function() {
    const isProduction = window.location.host.includes('fly.dev');
    const apiUrl = isProduction ?
        'https://taskmanager-hidden-sky-719.fly.dev/v3/api-docs' :
        'http://localhost:8080/v3/api-docs';

    window.ui = SwaggerUIBundle({
        url: apiUrl,
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
            if (isProduction && req.url.startsWith('/')) {
                req.url = 'https://taskmanager-hidden-sky-719.fly.dev' + req.url;
            }
            return req;
        }
    });
};