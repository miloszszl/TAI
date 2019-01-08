export function simpleHandleInputChange(e) {
    this.setState({
        [e.target.name]: e.target.value,
    });
}