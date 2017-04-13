import React from 'react';
import PropTypes from 'prop-types';

class InputField extends React.Component {
    render() {
        return (
            <div>
                <label>{this.props.label}:&nbsp;
                    <input type="text" name={this.props.attributeName}/>
                </label>
            </div>
        );
    }
}

InputField.propTypes = {
    attributeName: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
};

InputField.defaultProps = {
    label: '(label)',
};

export default InputField;
