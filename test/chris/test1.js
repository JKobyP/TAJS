//var x = "alert(\"Hacked baby\")";
//eval(x);
//--------------------------------------

//var x = "hello";
//for(var i = 0; i < 5; i++) {
//    x += "o";
//}
//
function Ninja(){
  var slices = 0;

  this.getSlices = function(){
    return slices;
  };
  this.slice = function(){
    slices++;
  };
}

var ninja = new Ninja();
ninja.slice();
TAJS_assert( ninja.getSlices() == 1 );
//x = ninja.getSlices();
TAJS_assert( ninja.slices === undefined );
//var y = "";
//var ar = ["h","i"];
//for(var i = 0; i < 2; i++) {
//    y += ar[i];
//}
//-------------------------------------
//var z = "";
//for(var p in {a:"a",b:"b"}) {
//    z += p;
//}

//var a = "Oi";
//var i = 2;
//while(i < 4) {
//    a += "i";
//    i++;
//}