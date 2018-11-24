local sprinklers = require("sprinklers")

local module = {}

function module.process(request)
    print("Processing requests ...")
    local _, _, method, req, major, minor = string.find(request, "([A-Z]+) (.+) HTTP/(%d).(%d)")
    
    if(string.find(req,"/sprinkler/start/1")) then
        sprinklers.startSprinkler(1)   
    end

    if(string.find(req,"/sprinkler/start/2")) then
        sprinklers.startSprinkler(2)
    end

    if(string.find(req,"/sprinkler/stop/1")) then
        sprinklers.stopSprinkler(1)
    end

    if(string.find(req,"/sprinkler/stop/2")) then
        sprinklers.stopSprinkler(2)
    end
    
    return sprinklers.status() 
end

return module