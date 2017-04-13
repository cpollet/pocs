import React from 'react';
import PropTypes from 'prop-types';

class SelectField extends React.Component {

    render() {
        return (
            <div>
                <label htmlFor={this.props.attributeName}>{this.props.label}</label>:&nbsp;
                <select id={this.props.attributeName}>
                    {this.props.options.map(o => (
                        <option value={o.value} key={this.props.attributeName + ':' + o.value}>{o.label}</option>
                    ))}
                </select>
            </div>
        );
    }
}

SelectField.propTypes = {
    attributeName: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    options: PropTypes.arrayOf(PropTypes.shape({
        value: PropTypes.string.isRequired,
        label: PropTypes.string.isRequired
    }))
};

SelectField.defaultProps = {
    label: '(label)',
    options: [],
};

export default SelectField;
