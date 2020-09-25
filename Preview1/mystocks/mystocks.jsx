
import React from "react";
import PropTypes from "prop-types";

import "./mystocks.css";

class Mystocks extends React.Component {

  constructor(props) {
      super(props);
      this.state = {

      };
  }
  
  render() {
    
    return (
          <div data-layer="4d0f3b1c-94f6-4f58-bfd2-ae8df98b59b0" className="mystocks">        <div data-layer="05afeeea-d67d-4ba9-aff7-ef26cb57e5d8" className="rectangle1"></div>
        <div data-layer="2c935b8d-36ae-4108-b716-638b1229d13d" className="group2">            <div data-layer="ec3fbd4a-ec6d-46c9-a120-6ac1710515a0" className="home">Home</div>
            <svg data-layer="62eec763-dff0-4510-b69f-f318e7de5970" preserveAspectRatio="none" viewBox="0 -0.5 70 1" className="line1"><path d="M 70 0 L 0 0"  /></svg>
            <svg data-layer="2e8fe56b-5bc3-4804-9684-eca383831485" preserveAspectRatio="none" viewBox="-0.34619140625 -0.360687255859375 25.6923828125 24.72137451171875" className="line2"><path d="M 0 24 L 25 0"  /></svg>
            <svg data-layer="8cb03fc2-6cf7-45b8-95e6-c6cb643346d0" preserveAspectRatio="none" viewBox="-0.373046875 -0.3330078125 25.74609375 28.666015625" className="line3"><path d="M 0 0 L 25 28"  /></svg>
</div>
        <div data-layer="b37fc300-67f9-428d-a32f-17b362498aff" className="myStocks">My Stocks</div>
</div>

    );
  }
}

Mystocks.propTypes = {

}

Mystocks.defaultProps = {

}


export default Mystocks;
          