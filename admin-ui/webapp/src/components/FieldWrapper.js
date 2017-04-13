import React from 'react';
import PropTypes from 'prop-types';

class FieldWrapper extends React.Component {
    componentDidMount() {
        this.props.onRender({
            attribute: this.props.attributeName
        });
    }


    render() {
        return (
            this.props.children
        );
    }
}

FieldWrapper.propTypes = {
    attributeName: PropTypes.string.isRequired,
    children: PropTypes.node.isRequired,
    onRender: PropTypes.func.isRequired
};

export default FieldWrapper;
