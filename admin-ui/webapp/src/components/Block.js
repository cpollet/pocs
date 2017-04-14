import React from 'react';
import PropTypes from 'prop-types';
import fetch from 'isomorphic-fetch';
import FieldWrapper from './FieldWrapper';

class Block extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.state = {
            mountedChildren: 0,
            attributes: [],
            childrenCount: React.Children.count(this.props.children),
            fetching: false,
            fetched: false,
            data: {}
        };
    }

    fetchData() {
        const self = this;
        this.setState(function (currentState) {
            if (currentState.fetching || currentState.fetched) {
                return currentState;
            }

            fetch('/api/attributes/' + window.lang + '?attributes=' + this.state.attributes.sort().join(','))
                .then(response => {
                    return response.json();
                })
                .then(json => {
                    self.setState({
                        fetching: false,
                        fetched: true,
                        data: json
                    });
                });

            return {
                fetching: true
            };
        });
    }

    onChildNodeDidMount(event) {
        this.setState(
            function (currentState) {
                return {
                    mountedChildren: currentState.mountedChildren + 1,
                    attributes: currentState.attributes.concat(event.attribute)
                };
            },
            function () {
                if (this.state.mountedChildren === this.state.childrenCount) {
                    this.fetchData();
                }
            }
        );
    }

    // Nasty hack!
    shouldComponentUpdate() {
        if (window['forceUpdate_' + this.props.name]) {
            window['forceUpdate_' + this.props.name] = false;
            this.setState(
                function () {
                    return {
                        fetched: false
                    };
                },
                function () {
                    this.fetchData();
                }
            );
        }

        return true;
    }

    render() {
        return (
            <div style={{
                border: '1px solid gray',
                padding: '1em',
                margin: '1em'
            }}>
                {React.Children.map(this.props.children, child => {
                    let childWithProps;
                    if (this.state.fetched === true) {
                        childWithProps = React.cloneElement(child, {
                            label: this.state.data[child.props.attributeName].label,
                            options: this.state.data[child.props.attributeName].values,
                            ready: true
                        });
                    } else {
                        childWithProps = React.cloneElement(child, {
                            label: child.props.attributeName,
                        });
                    }
                    return (
                        <FieldWrapper attributeName={child.props.attributeName}
                                      onRender={this.onChildNodeDidMount.bind(this)}>
                            {childWithProps}
                        </FieldWrapper>
                    );
                })}
            </div>
        );
    }
}

Block.propTypes = {
    children: PropTypes.node,
    name: PropTypes.string
};

export default Block;
