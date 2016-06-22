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

var secretword = cond ? "enter your secret word: " : "horse";
var one = guess("correct", secretword);
var two = guess("horse", secretword);
var three = guess("battery", secretword);
var four = guess("staple", secretword);

function guess(word, secret) {
    var cond1 = word === secret;
    if (cond1) {
        return true;
    } else {
        return false;
    }
}