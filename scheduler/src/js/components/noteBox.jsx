"use strict";
var React = require("react/addons");
var NoteBox = React.createClass({


  render: function() {
    return (
      <div className="note-box">
        <p> {this.props.text} </p>
      </div>
    );
  },
});

module.exports = NoteBox;
