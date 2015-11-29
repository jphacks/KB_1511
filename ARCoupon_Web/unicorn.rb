@dir = "/var/www/vhosts/ARCoupon_Web"
worker_processes 2
working_directory @dir
preload_app true
timeout 30
listen "#{@dir}/tmp/ARCoupon_Web.sock", :backlog => 64
pid "#{@dir}/tmp/pids/unicorn.pid"
stderr_path "#{@dir}/log/unicorn.stderr.log"
stdout_path "#{@dir}/log/unicorn.stdout.log"
