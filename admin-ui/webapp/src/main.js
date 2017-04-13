import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';

// Nasty hack!
window.forceUpdate = false;
window.lang = 'en';
window.switchLanguage = function () {
    if (window.lang == 'en') {
        window.lang = 'fr';
    } else {
        window.lang = 'en';
    }
    window.forceUpdate = true;
    window.app.forceUpdate();
    return true;
};

document.addEventListener('DOMContentLoaded', function () {
    ReactDOM.render((
        <App/>
    ), document.getElementById('root'));
});
