var auth_username = pm.variables.get("auth_username")
var auth_password = pm.variables.get("auth_password")
var client_id = pm.variables.get("client_id")
var client_secret  = pm.variables.get("client_secret")

var authBody = `username=${auth_username}&password=${auth_password}&grant_type=password&client_id=${client_id}&client_secret=${client_secret}`;
console.log(authBody)
var force_refresh = true
var token_expires_in = pm.environment.get("token_expires_in");
var token_created = pm.environment.get("token_created");
var expired = (new Date() - token_created) >= token_expires_in;

if(force_refresh ||  expired){
    console.log("Refreshing token.")
    refresh_token();
} else {
    console.log("Token not expired, reusing.")
}

function refresh_token(){
    pm.sendRequest({
        url:  pm.variables.get("auth_server_url"),
        method: 'POST',
        header: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: authBody
    }, function (err, res) {
        if(err) console.error(err);
        else {
            console.log("Fetched access_token");
            json = res.json()
            access_token = json.access_token
            token_expires_in = json.expires_in * 1000;
            token_created = new Date();
            pm.environment.set("token_expires_in", token_expires_in)
            pm.environment.set("access_token", access_token);
            pm.environment.set("token_created", token_created)
        }
    });
}