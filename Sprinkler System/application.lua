local net = require("net")
local wifi = require("wifi")
local requests = require("requests")

local config = require("config")

local module = {}

local function runServer()
  local s = net.createServer(net.TCP)

  print("====================================")
  print("Server Started")
  print("Open " .. wifi.sta.getip() .. " in your browser")
  print("====================================")

  s:listen(config.PORT, function(connection)
    connection:on("receive", function(c, request)
      print(request)
      local response = requests.process(request)
      c:send(response)
    end)

    connection:on("sent", function(c) c:close() end)
  end)
end

function module.start()
  runServer()
end

return module
