local sprinklers = require("sprinklers")
local headers = require("headers")

local module = {}

function module.process(c, request)
    print("Processing requests ...")
    local _, _, method, req, major, minor = string.find(request, "([A-Z]+) (.+) HTTP/(%d).(%d)")
    headers.buildHeader(c, 200, "json", false, false)
    return executeCommand(req)
end

function executeCommand(req)
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
