const myStates = ["delhi", "UP", "Rajasthan", "Assam", 1994, "MP"];

console.log("-------for--------");
for (let i = 0; i < myStates.length; i++) {
    console.log(myStates[i]);
}

console.log("-------while--------");
var i = 0;
while (i < myStates.length) {
    console.log(myStates[i]);
    i++;
}

console.log("-------do while--------");
var i = 0;
do {
    console.log(myStates[i]);
    i++;
} while (i < myStates.length);
