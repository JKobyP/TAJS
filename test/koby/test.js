var z = "h" + "i";

function greeting () {
    var x;
    if (Math.random() > .5) {
        x = "hello";
    } else {
        x = "goodbye";
    }
    return x;
}
var w = greeting();
var y = w + "something, world!".substring(9);
