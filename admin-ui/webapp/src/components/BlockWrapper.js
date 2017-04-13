import React from 'react';
import PropTypes from 'prop-types';

class BlockWrapper extends React.Component {
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

BlockWrapper.propTypes = {
    attributeName: PropTypes.string.isRequired,
    children: PropTypes.node.isRequired,
    onRender: PropTypes.func.isRequired
};

export default BlockWrapper;
