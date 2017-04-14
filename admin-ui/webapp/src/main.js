import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';

// Nasty hack!
window.forceUpdate_block1 = false;
window.forceUpdate_block2 = true;
window.lang = 'en';
window.switchLanguage = function () {
    if (window.lang == 'en') {
        window.lang = 'fr';
    } else {
        window.lang = 'en';
    }
    window.forceUpdate_block1 = true;
    window.forceUpdate_block2 = true;
    window.app.forceUpdate();
};

document.addEventListener('DOMContentLoaded', function () {
    ReactDOM.render((
        <App/>
    ), document.getElementById('root'));
});
