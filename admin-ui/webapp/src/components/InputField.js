import React from 'react';
import PropTypes from 'prop-types';
import renderIf from '../renderif';

class InputField extends React.Component {
    render() {
        return (
            <div>
                <label>{this.props.label}:&nbsp;
                    {renderIf(this.props.ready, <input type="text" name={this.props.attributeName}/>)}
                </label>
            </div>
        );
    }
}

InputField.propTypes = {
    attributeName: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    ready: PropTypes.bool.isRequired
};

InputField.defaultProps = {
    label: '(label)',
    ready: false
};

export default InputField;
