import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from "react-router-dom";
import {Provider} from "react-redux";
import ConfigureStore from "./store/ConfigureStore";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import GlobalStyle from "./shared/GlobalStyle";

const queryClient = new QueryClient();
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <QueryClientProvider client={queryClient}>
        <GlobalStyle />
        <Provider store={ConfigureStore}>
            <BrowserRouter>
                <App/>
            </BrowserRouter>
        </Provider>
    </QueryClientProvider>
);

reportWebVitals();
