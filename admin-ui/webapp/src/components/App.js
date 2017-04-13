import React from 'react';
import SelectField from './SelectField';
import Block from './Block';

class App extends React.Component {
    componentDidMount() {
        // Nasty hack!
        window.app = this;
    }

    render() {
        return (
            <Block>
                <SelectField attributeName="gender"/>
                <SelectField attributeName="maritalStatus"/>
            </Block>
        );
    }
}

App.propTypes = {};

export default App;
