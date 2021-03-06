import React from 'react';
import Block from './Block';
import SelectField from './SelectField';
import RadioField from './RadioField';
import CheckboxField from './CheckboxField';
import InputField from './InputField';

class App extends React.Component {
    componentDidMount() {
        // Nasty hack!
        window.app = this;
    }

    render() {
        return (
            <div>
                <Block name="block1">
                    <InputField attributeName="username"/>
                </Block>
                <Block name="block2">
                    <RadioField attributeName="gender"/>
                    <SelectField attributeName="maritalStatus"/>
                    <CheckboxField attributeName="socialNet"/>
                </Block>
            </div>
        );
    }
}

App.propTypes = {};

export default App;
