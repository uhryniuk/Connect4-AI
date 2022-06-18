

async function serverPOST(rawJSON, endpoint){
    const res = await fetch("http://localhost:8080"+endpoint, {
        method: "POST",
        body: rawJSON,
        headers: {
            "Content-Type": "application/json"
        },
    })

    return res.json()

}

window.onload = () => {



    let sendRandom = document.getElementById("random-button")
    
    
    sendRandom.onclick = () => {
        const serverRes = serverPOST(
            '{"name":"John", "age":30, "car":null}', 
            '/random'
        )

        serverRes.then(data => {console.log(data)})
            .catch(error => {console.log(error)})

    }

}    





