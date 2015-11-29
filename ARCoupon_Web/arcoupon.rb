require 'sinatra/base'
require 'sinatra/reloader'
require 'active_record'
require 'mysql2'
require 'json'

ActiveRecord::Base.configurations = YAML.load_file('database.yml')
ActiveRecord::Base.establish_connection(:production)

class Coupon < ActiveRecord::Base
end

class Arcoupon < Sinatra::Base
  configure :development do
    register Sinatra::Reloader
  end

  # index
  get '/' do
    erb :index
  end

  # 全クーポンの取得
  get '/coupons' do
      coupons = Coupon.all
      coupons.to_json(:root => false)
  end

  # :id と一致するクーポンの取得
  get '/coupons/:id' do
      coupon = Coupon.find(params['id'])
      coupon.to_json(:root => false)
  end

  # /search?lat=123&lng=456 で検索
  # 条件に合ったものを取得
  get '/coupons/search' do
    params['lat']
    params['lng']
  end

  # クーポンの登録
  post '/coupons' do
      request.body.rewind
      params = JSON.parse request.body.read

      coupon = Coupon.new
      coupon.shop_name = params['shop_name']
      coupon.lat = params['lat']
      coupon.lng = params['lng']
      coupon.detail = params['detail']
      coupon.save
  end

  # :id と一致するクーポンの更新
  put '/coupons/:id' do

  end

  # :id と一致するクーポンを削除
  delete '/coupons/:id' do

  end
end
