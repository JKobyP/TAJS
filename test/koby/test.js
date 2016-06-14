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
var y = greeting() + "something, world!".substring(9);
