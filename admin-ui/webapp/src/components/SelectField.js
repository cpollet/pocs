import React from 'react';
import PropTypes from 'prop-types';
import renderIf from '../renderif';

class SelectField extends React.Component {
    render() {
        return (
            <div>
                <label>{this.props.label}:&nbsp;
                    {renderIf(this.props.ready,
                        <select name={this.props.attributeName}>
                            {this.props.options.map(o => (
                                <option value={o.value}
                                        key={this.props.attributeName + ':' + o.value}>{o.label}</option>
                            ))}
                        </select>
                    )}
                </label>
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
    })),
    ready: PropTypes.bool.isRequired
};

SelectField.defaultProps = {
    label: '(label)',
    options: [],
    ready: false
};

export default SelectField;
