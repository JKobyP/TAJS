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
