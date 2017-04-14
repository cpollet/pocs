import React from 'react';
import PropTypes from 'prop-types';

class CheckboxField extends React.Component {
    render() {
        return (
            <div>
                {this.props.label}:&nbsp;
                {this.props.options.map(o => (
                    <label key={this.props.attributeName + ':' + o.value + ':label'}>
                        <input type="checkbox" name={this.props.attributeName} value={o.value}
                               key={this.props.attributeName + ':' + o.value + ':input'}/> {o.label}
                        &nbsp;
                    </label>
                ))}
            </div>
        );
    }
}

CheckboxField.propTypes = {
    attributeName: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    options: PropTypes.arrayOf(PropTypes.shape({
        value: PropTypes.string.isRequired,
        label: PropTypes.string.isRequired
    })),
    ready: PropTypes.bool.isRequired
};

CheckboxField.defaultProps = {
    label: '(label)',
    options: [],
    ready: false
};

export default CheckboxField;
