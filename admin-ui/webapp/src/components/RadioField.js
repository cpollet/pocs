import React from 'react';
import PropTypes from 'prop-types';

class RadioField extends React.Component {
    render() {
        return (
            <div>
                {this.props.label}:&nbsp;
                {this.props.options.map(o => (
                    <label key={this.props.attributeName + ':' + o.value + ':label'}>
                        <input type="radio" name={this.props.attributeName} value={o.value}
                               key={this.props.attributeName + ':' + o.value + ':input'}/> {o.label}
                        &nbsp;
                    </label>
                ))}
            </div>
        );
    }
}

RadioField.propTypes = {
    attributeName: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    options: PropTypes.arrayOf(PropTypes.shape({
        value: PropTypes.string.isRequired,
        label: PropTypes.string.isRequired
    }))
};

RadioField.defaultProps = {
    label: '(label)',
    options: [],
};

export default RadioField;
