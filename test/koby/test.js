var z = "h" + "i";
var cond = Math.random() > .5;

function greeting () {
    var x;
    if (cond) {
        x = "hello";
    } else {
        x = "goodbye";
    }
    return x;
}
var w = greeting();
var y = w + "something, world!".substring(9);

