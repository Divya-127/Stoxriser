
import React from "react";
import PropTypes from "prop-types";

import "./myaccount.css";

class Myaccount extends React.Component {

  constructor(props) {
      super(props);
      this.state = {

      };
  }
  
  render() {
    
    return (
          <div data-layer="027b69cd-4b0f-4880-ae51-339830f29b4b" className="myaccount">        <div data-layer="96361c0d-e404-481d-8f3e-741fbcc95be7" className="rectangle1"></div>
        <div data-layer="b4f8f1fd-0b74-4f0e-9715-73daaa9f9d81" className="group2">            <div data-layer="957c2155-19a4-4aac-9671-9c439e00c46c" className="home">Home</div>
            <svg data-layer="1f66e775-6248-4d75-9056-46ebaff1a87c" preserveAspectRatio="none" viewBox="0 -0.5 70 1" className="line1"><path d="M 70 0 L 0 0"  /></svg>
            <svg data-layer="028476ef-475d-458d-9370-2bb7a3660738" preserveAspectRatio="none" viewBox="-0.3466796875 -0.360687255859375 25.693359375 24.72137451171875" className="line2"><path d="M 0 24 L 25 0"  /></svg>
            <svg data-layer="87c112a6-4d7a-4e54-b3ed-4f23910f850d" preserveAspectRatio="none" viewBox="-0.373046875 -0.3330078125 25.74609375 28.666015625" className="line3"><path d="M 0 0 L 25 28"  /></svg>
</div>
        <div data-layer="9adc54dc-8b1c-4556-894d-047e00d8a31c" className="myAccount">My Account</div>
</div>

    );
  }
}

Myaccount.propTypes = {

}

Myaccount.defaultProps = {

}


export default Myaccount;
          