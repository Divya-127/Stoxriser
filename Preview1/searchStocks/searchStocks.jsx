
import React from "react";
import PropTypes from "prop-types";

import "./searchStocks.css";

class SearchStocks extends React.Component {

  constructor(props) {
      super(props);
      this.state = {

      };
  }
  
  render() {
    
    return (
          <div data-layer="2cdc2a3e-6740-4d23-a4c0-0628e605449d" className="searchStocks">        <div data-layer="2fb44ffc-d2ac-4454-a1bd-86bfae47d23a" className="rectangle1"></div>
        <div data-layer="fe9f497a-de9e-4cc3-ad53-35d0f5cc09b3" className="group2">            <div data-layer="a6b99254-04cb-4529-87b8-8b964baff22b" className="home">Home</div>
            <svg data-layer="1884a551-acfe-4be4-9d44-bd22ed7c3b1e" preserveAspectRatio="none" viewBox="0 -0.5 70 1" className="line1"><path d="M 70 0 L 0 0"  /></svg>
            <svg data-layer="d3a92255-190a-4de1-b905-76def92e008b" preserveAspectRatio="none" viewBox="-0.34619140625 -0.360687255859375 25.6923828125 24.72137451171875" className="line2"><path d="M 0 24 L 25 0"  /></svg>
            <svg data-layer="d7df77c6-9d20-4ea8-825e-582d0d099d9e" preserveAspectRatio="none" viewBox="-0.373046875 -0.3330078125 25.74609375 28.666015625" className="line3"><path d="M 0 0 L 25 28"  /></svg>
</div>
        <div data-layer="fc2d8261-cb27-42eb-8c1c-9100da1555d9" className="searchStocksf76d8630">Search Stocks</div>
</div>

    );
  }
}

SearchStocks.propTypes = {

}

SearchStocks.defaultProps = {

}


export default SearchStocks;
          