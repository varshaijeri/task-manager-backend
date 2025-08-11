window.onload = function() {
    const ui = SwaggerUIBundle({
        url: "/v3/api-docs",
        dom_id: '#swagger-ui',
        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],
        plugins: [
            SwaggerUIBundle.plugins.DownloadUrl
        ],
        layout: "StandaloneLayout",
        requestInterceptor: (req) => {
            const token = localStorage.getItem('swagger_token');
            if (token) {
                req.headers.Authorization = 'Bearer ' + token;
            }
            return req;
        },
        responseInterceptor: (res) => {
            if (res.url.endsWith('/login') && res.ok) {
                const token = res.obj.token; // Adjust based on your response structure
                localStorage.setItem('swagger_token', token);
            }
            return res;
        }
    });

    window.ui = ui;
};