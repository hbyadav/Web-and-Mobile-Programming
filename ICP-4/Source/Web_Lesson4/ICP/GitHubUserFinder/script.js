function getGithubInfo(user) {
    var url = 'https://api.github.com/users/' + user;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('GET', url, false);
    xmlhttp.send();

    return xmlhttp;

    //console.log(data);
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)
}

function showUser(user) {
    document.querySelector("#profile h2").innerHTML = "Profile found";
    document.querySelector("#profile .information").innerHTML = user.login;
    document.querySelector("#profile .avatar").innerHTML = "<img src="+user.avatar_url+">";
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
}

function noSuchUser(user) {
    document.querySelector("#profile h2").innerHTML = "Profile not found";
    document.querySelector("#profile .information").innerHTML = "";
    document.querySelector("#profile .avatar").innerHTML = "";
    //3. set the elements such that a suitable message is displayed
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
