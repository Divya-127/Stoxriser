import React, {Component} from 'react';
import PropTypes from "prop-types";
import {StyleSheet, Text, View, TextInput, FlatList, Picker, ScrollView, TouchableHighlight} from 'react-native';
import {Image as ReactImage} from 'react-native';
import Svg, {Defs, Pattern} from 'react-native-svg';
import {Path as SvgPath} from 'react-native-svg';
import {Text as SvgText} from 'react-native-svg';
import {Image as SvgImage} from 'react-native-svg';

export default class SearchStocks extends Component {

  constructor(props) {
      super(props);
      this.state = {
          
      };
  }


  handlePress(target, owner) {
    if (this.props.onPress) {
        let name;
        let id;
        let index = -1;
        if (target.search("::") > -1) {
            const varCount = target.split("::").length;
            if (varCount === 2) {
                name = target.split("::")[0];
                id = target.split("::")[1];
            } else if (varCount === 3) {
                name = target.split("::")[0];
                index = parseInt(target.split("::")[1]);
                id = target.split("::")[2];
            }
        } else {
            name = target;
        }
        this.props.onPress({ type: 'button', name: name, index: index, id: id, owner: owner });
    }
  }

  handleChangeTextinput(name, value) {
      let id;
      let index = -1;
      if (name.search('::') > -1) {
          const varCount = name.split("::").length;
          if (varCount === 2) {
              name = name.split("::")[0];
              id = name.split("::")[1];
          } else if (varCount === 3) {
              name = name.split("::")[0];
              index = name.split("::")[1];
              id = name.split("::")[2];
          }
      } else {
          name = name;
      }
      let state = this.state;
      state[name.split('::').join('')] = value;
      this.setState(state, () => {
          if (this.props.onChange) {
              this.props.onChange({ type: 'textinput', name: name, value: value, index: index, id: id });
          }
      });
  }

  render() {
    
    return (
    <ScrollView data-layer="2cdc2a3e-6740-4d23-a4c0-0628e605449d" style={styles.searchStocks}>
        <ReactImage data-layer="2fb44ffc-d2ac-4454-a1bd-86bfae47d23a" source={require('./assets/rectangle1.png')} style={styles.searchStocks_rectangle1} />
        <View data-layer="fe9f497a-de9e-4cc3-ad53-35d0f5cc09b3" style={styles.searchStocks_group2}>
            <Text data-layer="a6b99254-04cb-4529-87b8-8b964baff22b" style={styles.searchStocks_group2_home}>Home</Text>
            <Svg data-layer="1884a551-acfe-4be4-9d44-bd22ed7c3b1e" style={styles.searchStocks_group2_line1} preserveAspectRatio="none" viewBox="0 -0.5 70 1" fill="transparent"><SvgPath d="M 70 0 L 0 0"  /></Svg>
            <Svg data-layer="d3a92255-190a-4de1-b905-76def92e008b" style={styles.searchStocks_group2_line2} preserveAspectRatio="none" viewBox="-0.34619140625 -0.360687255859375 25.6923828125 24.72137451171875" fill="transparent"><SvgPath d="M 0 24 L 25 0"  /></Svg>
            <Svg data-layer="d7df77c6-9d20-4ea8-825e-582d0d099d9e" style={styles.searchStocks_group2_line3} preserveAspectRatio="none" viewBox="-0.373046875 -0.3330078125 25.74609375 28.666015625" fill="transparent"><SvgPath d="M 0 0 L 25 28"  /></Svg>
        </View>
        <Text data-layer="fc2d8261-cb27-42eb-8c1c-9100da1555d9" style={styles.searchStocks_searchStocksf76d8630}>Search Stocks</Text>
    </ScrollView>
    );
  }
}

SearchStocks.propTypes = {

}

SearchStocks.defaultProps = {

}


const styles = StyleSheet.create({
  "searchStocks": {
    "opacity": 1,
    "position": "relative",
    "backgroundColor": "rgba(255, 255, 255, 1)",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "paddingTop": 0,
    "paddingRight": 0,
    "paddingBottom": 0,
    "paddingLeft": 0,
    "width": 1920,
    "height": 900,
    "left": 0,
    "top": 0
  },
  "searchStocks_rectangle1": {
    "opacity": 1,
    "position": "absolute",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "borderTopLeftRadius": 32,
    "borderTopRightRadius": 32,
    "borderBottomLeftRadius": 32,
    "borderBottomRightRadius": 32,
    "width": 1920,
    "height": 214,
    "left": 0,
    "top": 0
  },
  "searchStocks_group2": {
    "opacity": 1,
    "position": "absolute",
    "backgroundColor": "transparent",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "paddingTop": 0,
    "paddingRight": 0,
    "paddingBottom": 0,
    "paddingLeft": 0,
    "width": 233.5,
    "height": 52.5,
    "left": 70.5,
    "top": 83
  },
  "searchStocks_group2_home": {
    "opacity": 1,
    "position": "absolute",
    "backgroundColor": "rgba(255, 255, 255, 0)",
    "color": "rgba(0, 0, 0, 1)",
    "fontSize": 40,
    "fontWeight": "400",
    "fontStyle": "normal",
    "fontFamily": "Montserrat",
    "textAlign": "left",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "paddingTop": 0,
    "paddingRight": 0,
    "paddingBottom": 0,
    "paddingLeft": 0,
    "width": 124,
    "height": 49,
    "left": 109.5,
    "top": 0
  },
  "searchStocks_group2_line1": {
    "opacity": 1,
    "position": "absolute",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "paddingTop": 0,
    "paddingRight": 0,
    "paddingBottom": 0,
    "paddingLeft": 0,
    "width": 70,
    "height": 1,
    "left": 0,
    "top": 24
  },
  "searchStocks_group2_line2": {
    "opacity": 1,
    "position": "absolute",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "paddingTop": 0,
    "paddingRight": 0,
    "paddingBottom": 0,
    "paddingLeft": 0,
    "width": 25.69,
    "height": 24.72,
    "left": -0.35,
    "top": 0.14
  },
  "searchStocks_group2_line3": {
    "opacity": 1,
    "position": "absolute",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "paddingTop": 0,
    "paddingRight": 0,
    "paddingBottom": 0,
    "paddingLeft": 0,
    "width": 25.75,
    "height": 28.67,
    "left": -0.37,
    "top": 24.17
  },
  "searchStocks_searchStocksf76d8630": {
    "opacity": 1,
    "position": "absolute",
    "backgroundColor": "rgba(255, 255, 255, 0)",
    "color": "rgba(0, 0, 0, 1)",
    "fontSize": 60,
    "fontWeight": "700",
    "fontStyle": "normal",
    "fontFamily": "Montserrat",
    "textAlign": "left",
    "marginTop": 0,
    "marginRight": 0,
    "marginBottom": 0,
    "marginLeft": 0,
    "paddingTop": 0,
    "paddingRight": 0,
    "paddingBottom": 0,
    "paddingLeft": 0,
    "width": 439,
    "height": 73,
    "left": 1180,
    "top": 71
  }
});