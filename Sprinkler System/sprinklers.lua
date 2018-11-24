local module = {}

local sprinkler1 = 1
local sprinkler2 = 2
gpio.mode(sprinkler1, gpio.OUTPUT)
gpio.mode(sprinkler2, gpio.OUTPUT)

function module.startSprinkler(id)
    if (id == 1)then
        gpio.write(sprinkler1, gpio.LOW)
        return true
    elseif(id == 2)then 
        gpio.write(sprinkler2, gpio.LOW)
        return true
    end
    return false
end

function module.stopSprinkler(id)
    if (id == 1)then
        gpio.write(sprinkler1, gpio.HIGH)
        return true
    elseif(id == 2)then 
        gpio.write(sprinkler2, gpio.HIGH)
        return true
    end
    return false
end

function module.status()
    return string.format("{\"sprinkler1\" : %s, \"sprinkler2\" : %s}", tostring(gpio.read(sprinkler1) == gpio.LOW), tostring(gpio.read(sprinkler2) == gpio.LOW))
end

return module
