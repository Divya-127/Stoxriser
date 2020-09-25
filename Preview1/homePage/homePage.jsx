
import React from "react";
import PropTypes from "prop-types";

import "./homePage.css";

class HomePage extends React.Component {

  constructor(props) {
      super(props);
      this.state = {

      };
  }
  
  render() {
    
    return (
          <div data-layer="8e90f04a-5ea7-4a36-a2ad-4c67854fc76e" className="homePage">        <div data-layer="9fb54cc0-8308-4ff3-9c6d-f35830a7583b" className="stockpic"></div>
        <div data-layer="71e73d9a-05e2-4064-a64e-32db7dad8d45" className="rectangle1"></div>
        <div data-layer="3691eed8-ceb6-43c3-a8a9-7705f310946c" className="stoxriser">Stoxriser</div>
        <div data-layer="bb11507f-b336-4803-81eb-e11cefcd5b8e" className="searchStocks">Search Stocks</div>
        <div data-layer="61ac3fb8-b387-45aa-b831-ac7c03a21ea9" className="group1">            <div data-layer="38a2c5bd-dfb5-43c4-9fee-d7433222da2c" className="myStocks">My Stocks</div>
</div>
        <div data-layer="24948adb-20e3-4aa8-b6bf-454d3d0b57a4" className="myAccount">My Account</div>
</div>

    );
  }
}

HomePage.propTypes = {

}

HomePage.defaultProps = {

}


export default HomePage;
          