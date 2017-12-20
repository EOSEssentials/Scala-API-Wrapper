package services

import play.api.libs.json.{JsValue, Json}
import utils.{Client, Settings}

import scala.concurrent.Future

object ChainAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/chain/$r")

  /*
  {
    "server_version": "00db493c",
    "head_block_num": 671327,
    "last_irreversible_block_num": 671313,
    "head_block_id": "000a3e5f05573f275251075a8baf091ea6eca216a1746914aabe99d3a330866e",
    "head_block_time": "2017-12-20T22:21:14",
    "head_block_producer": "initp",
    "recent_slots": "1111111111111111111111111111111111111111111111111111111111111111",
    "participation_rate": "1.00000000000000000"
  }
   */
  def getInfo: Future[Option[JsValue]] =
    Client.get(route("get_info"))

  /*
  {
    "previous": "000a3e06be0017aef4755a028856536fc7146f7d561ef63601c948b8d795602b",
    "timestamp": "2017-12-20T22:18:18",
    "transaction_merkle_root": "0000000000000000000000000000000000000000000000000000000000000000",
    "producer": "initr",
    "producer_changes": [],
    "producer_signature": "1f6f8968fddb0b8b485f8402540edd265267fbdbfa37a589b65014a0caf8bb940f1af39258a7bbdc890a941dbc6092d45913af71416777a2ed03d61c43273ba87a",
    "cycles": [],
    "id": "000a3e07e47e0e5d3717f858bda40d1ffb04d8154b301f2f47cdfce529acd80a",
    "block_num": 671239,
    "ref_block_prefix": 1492653879
  }
   */
  def getBlock(blockNumOrId:String): Future[Option[JsValue]] =
    Client.post(route("get_block"), Json.obj("block_num_or_id" -> blockNumOrId))

  /*
  {
    "account_name": "inita",
    "eos_balance": "991772.9100 EOS",
    "staked_balance": "0.0000 EOS",
    "unstaking_balance": "0.0000 EOS",
    "last_unstaking_time": "1969-12-31T23:59:59",
    "permissions": [
            {
                "perm_name": "active",
                "parent": "owner",
                "required_auth": {
                    "threshold": 1,
                    "keys": [
                        {
                            "key": "EOS8DiJq19uTm5wu2qFzFUt5JXw8akoJ99kKyKyL7KqLvjZ1FRFXh",
                            "weight": 1
                        }
                    ],
                    "accounts": []
                }
            },
            {
                "perm_name": "owner",
                "parent": "",
                "required_auth": {
                    "threshold": 1,
                    "keys": [
                        {
                            "key": "EOS8DiJq19uTm5wu2qFzFUt5JXw8akoJ99kKyKyL7KqLvjZ1FRFXh",
                            "weight": 1
                        }
                    ],
                    "accounts": []
                }
            }
        ]
    }
   */
  def getAccount(accountName:String): Future[Option[JsValue]] =
    Client.post(route("get_account"), Json.obj("account_name" -> accountName))

  /*
  {
    "account_name": "currency",
    "code_hash": "86968a9091ce32255777e2017fccaede8cea2d4978b30f25b41ee97b9d77bed0",
    "wast": "(module\n  (type $0 (func (param i64 i64 i32) (result i32)))\n  (type $1 (func (param i64 i64 i32 i32) (result i32)))\n  (type $2 (func (param i64)))\n  (type $3 (func (param i64 i64 i64 i32 i32) (result i32)))\n  (type $4 (func (param i32 i32)))\n  (type $5 (func (param i32 i32) (result i32)))\n  (type $6 (func (param i64 i32)))\n  (type $7 (func (param i32)))\n  (type $8 (func ))\n  (type $9 (func (param i64 i64)))\n  (import \"env\" \"assert\" (func $assert (param i32 i32)))\n  (import \"env\" \"load_i64\" (func $load_i64 (param i64 i64 i64 i32 i32) (result i32)))\n  (import \"env\" \"read_message\" (func $read_message (param i32 i32) (result i32)))\n  (import \"env\" \"remove_i64\" (func $remove_i64 (param i64 i64 i32) (result i32)))\n  (import \"env\" \"require_auth\" (func $require_auth (param i64)))\n  (import \"env\" \"require_notice\" (func $require_notice (param i64)))\n  (import \"env\" \"store_i64\" (func $store_i64 (param i64 i64 i32 i32) (result i32)))\n  (export \"memory\" (memory $11))\n  (export \"_ZN8currency13store_accountEyRKNS_7accountE\" (func $_ZN8currency13store_accountEyRKNS_7accountE))\n  (export \"_ZN8currency23apply_currency_transferERKNS_8transferE\" (func $_ZN8currency23apply_currency_transferERKNS_8transferE))\n  (export \"init\" (func $init))\n  (export \"apply\" (func $apply))\n  (memory $11 1)\n  (table $10 0 anyfunc)\n  (data $11 (i32.const 4)\n    \"\\c0\\04\\00\\00\")\n  (data $11 (i32.const 16)\n    \"account\\00\")\n  (data $11 (i32.const 32)\n    \"integer underflow subtracting token balance\\00\")\n  (data $11 (i32.const 80)\n    \"integer overflow adding token balance\\00\")\n  (data $11 (i32.const 128)\n    \"currency\\00\")\n  (data $11 (i32.const 144)\n    \"transfer\\00\")\n  (data $11 (i32.const 160)\n    \"message shorter than expected\\00\")\n  \n  (func $_ZN8currency13store_accountEyRKNS_7accountE\n    (param $0 i64)\n    (param $1 i32)\n    block $block\n      get_local $1\n      i64.load offset=8\n      i64.const 0\n      i64.eq\n      br_if $block\n      get_local $0\n      i64.const 3607749778735104000\n      get_local $1\n      i32.const 16\n      call $store_i64\n      drop\n      return\n    end ;; $block\n    get_local $0\n    i64.const 3607749778735104000\n    get_local $1\n    call $remove_i64\n    drop\n    )\n  \n  (func $_ZN8currency23apply_currency_transferERKNS_8transferE\n    (param $0 i32)\n    (local $1 i64)\n    (local $2 i32)\n    (local $3 i32)\n    (local $4 i64)\n    (local $5 i64)\n    (local $6 i64)\n    (local $7 i64)\n    (local $8 i32)\n    i32.const 0\n    i32.const 0\n    i32.load offset=4\n    i32.const 32\n    i32.sub\n    tee_local $8\n    i32.store offset=4\n    get_local $0\n    i64.load\n    set_local $5\n    get_local $0\n    i64.load offset=8\n    call $require_notice\n    get_local $5\n    call $require_notice\n    get_local $0\n    i64.load\n    call $require_auth\n    get_local $0\n    i64.load\n    set_local $1\n    i64.const 0\n    set_local $5\n    i64.const 59\n    set_local $4\n    i32.const 16\n    set_local $3\n    i64.const 0\n    set_local $6\n    loop $loop\n      block $block\n        block $block1\n          block $block2\n            block $block3\n              block $block4\n                get_local $5\n                i64.const 6\n                i64.gt_u\n                br_if $block4\n                get_local $3\n                i32.load8_s\n                tee_local $2\n                i32.const -97\n                i32.add\n                i32.const 255\n                i32.and\n                i32.const 25\n                i32.gt_u\n                br_if $block3\n                get_local $2\n                i32.const 165\n                i32.add\n                set_local $2\n                br $block2\n              end ;; $block4\n              i64.const 0\n              set_local $7\n              get_local $5\n              i64.const 11\n              i64.le_u\n              br_if $block1\n              br $block\n            end ;; $block3\n            get_local $2\n            i32.const 208\n            i32.add\n            i32.const 0\n            get_local $2\n            i32.const -49\n            i32.add\n            i32.const 255\n            i32.and\n            i32.const 5\n            i32.lt_u\n            select\n            set_local $2\n          end ;; $block2\n          get_local $2\n          i64.extend_u/i32\n          i64.const 56\n          i64.shl\n          i64.const 56\n          i64.shr_s\n          set_local $7\n        end ;; $block1\n        get_local $7\n        i64.const 31\n        i64.and\n        get_local $4\n        i64.const 4294967295\n        i64.and\n        i64.shl\n        set_local $7\n      end ;; $block\n      get_local $3\n      i32.const 1\n      i32.add\n      set_local $3\n      get_local $5\n      i64.const 1\n      i64.add\n      set_local $5\n      get_local $7\n      get_local $6\n      i64.or\n      set_local $6\n      get_local $4\n      i64.const -5\n      i64.add\n      tee_local $4\n      i64.const -6\n      i64.ne\n      br_if $loop\n    end ;; $loop\n    i64.const 0\n    set_local $5\n    get_local $8\n    i64.const 0\n    i64.store offset=24\n    get_local $8\n    get_local $6\n    i64.store offset=16\n    get_local $1\n    i64.const 5093418677655568384\n    i64.const 3607749778735104000\n    get_local $8\n    i32.const 16\n    i32.add\n    i32.const 16\n    call $load_i64\n    drop\n    get_local $0\n    i32.const 8\n    i32.add\n    i64.load\n    set_local $1\n    i64.const 59\n    set_local $4\n    i32.const 16\n    set_local $3\n    i64.const 0\n    set_local $6\n    loop $loop1\n      block $block5\n        block $block6\n          block $block7\n            block $block8\n              block $block9\n                get_local $5\n                i64.const 6\n                i64.gt_u\n                br_if $block9\n                get_local $3\n                i32.load8_s\n                tee_local $2\n                i32.const -97\n                i32.add\n                i32.const 255\n                i32.and\n                i32.const 25\n                i32.gt_u\n                br_if $block8\n                get_local $2\n                i32.const 165\n                i32.add\n                set_local $2\n                br $block7\n              end ;; $block9\n              i64.const 0\n              set_local $7\n              get_local $5\n              i64.const 11\n              i64.le_u\n              br_if $block6\n              br $block5\n            end ;; $block8\n            get_local $2\n            i32.const 208\n            i32.add\n            i32.const 0\n            get_local $2\n            i32.const -49\n            i32.add\n            i32.const 255\n            i32.and\n            i32.const 5\n            i32.lt_u\n            select\n            set_local $2\n          end ;; $block7\n          get_local $2\n          i64.extend_u/i32\n          i64.const 56\n          i64.shl\n          i64.const 56\n          i64.shr_s\n          set_local $7\n        end ;; $block6\n        get_local $7\n        i64.const 31\n        i64.and\n        get_local $4\n        i64.const 4294967295\n        i64.and\n        i64.shl\n        set_local $7\n      end ;; $block5\n      get_local $3\n      i32.const 1\n      i32.add\n      set_local $3\n      get_local $5\n      i64.const 1\n      i64.add\n      set_local $5\n      get_local $7\n      get_local $6\n      i64.or\n      set_local $6\n      get_local $4\n      i64.const -5\n      i64.add\n      tee_local $4\n      i64.const -6\n      i64.ne\n      br_if $loop1\n    end ;; $loop1\n    get_local $8\n    get_local $6\n    i64.store\n    get_local $8\n    i64.const 0\n    i64.store offset=8\n    get_local $1\n    i64.const 5093418677655568384\n    i64.const 3607749778735104000\n    get_local $8\n    i32.const 16\n    call $load_i64\n    drop\n    get_local $8\n    i32.const 24\n    i32.add\n    tee_local $3\n    i64.load\n    get_local $0\n    i64.load offset=16\n    i64.ge_u\n    i32.const 32\n    call $assert\n    get_local $3\n    get_local $3\n    i64.load\n    get_local $0\n    i64.load offset=16\n    tee_local $5\n    i64.sub\n    i64.store\n    get_local $5\n    get_local $8\n    i64.load offset=8\n    i64.add\n    get_local $5\n    i64.ge_u\n    i32.const 80\n    call $assert\n    get_local $8\n    get_local $8\n    i64.load offset=8\n    get_local $0\n    i64.load offset=16\n    i64.add\n    i64.store offset=8\n    get_local $0\n    i64.load\n    set_local $5\n    block $block10\n      block $block11\n        get_local $3\n        i64.load\n        i64.const 0\n        i64.eq\n        br_if $block11\n        get_local $5\n        i64.const 3607749778735104000\n        get_local $8\n        i32.const 16\n        i32.add\n        i32.const 16\n        call $store_i64\n        drop\n        br $block10\n      end ;; $block11\n      get_local $5\n      i64.const 3607749778735104000\n      get_local $8\n      i32.const 16\n      i32.add\n      call $remove_i64\n      drop\n    end ;; $block10\n    get_local $0\n    i32.const 8\n    i32.add\n    i64.load\n    set_local $5\n    block $block12\n      block $block13\n        get_local $8\n        i32.const 8\n        i32.add\n        i64.load\n        i64.const 0\n        i64.eq\n        br_if $block13\n        get_local $5\n        i64.const 3607749778735104000\n        get_local $8\n        i32.const 16\n        call $store_i64\n        drop\n        br $block12\n      end ;; $block13\n      get_local $5\n      i64.const 3607749778735104000\n      get_local $8\n      call $remove_i64\n      drop\n    end ;; $block12\n    i32.const 0\n    get_local $8\n    i32.const 32\n    i32.add\n    i32.store offset=4\n    )\n  \n  (func $init\n    (local $0 i32)\n    (local $1 i32)\n    (local $2 i64)\n    (local $3 i64)\n    (local $4 i64)\n    (local $5 i64)\n    (local $6 i64)\n    (local $7 i32)\n    i32.const 0\n    i32.const 0\n    i32.load offset=4\n    i32.const 32\n    i32.sub\n    tee_local $7\n    i32.store offset=4\n    i64.const 0\n    set_local $3\n    i64.const 59\n    set_local $2\n    i32.const 16\n    set_local $1\n    i64.const 0\n    set_local $4\n    loop $loop\n      block $block\n        block $block1\n          block $block2\n            block $block3\n              block $block4\n                get_local $3\n                i64.const 6\n                i64.gt_u\n                br_if $block4\n                get_local $1\n                i32.load8_s\n                tee_local $0\n                i32.const -97\n                i32.add\n                i32.const 255\n                i32.and\n                i32.const 25\n                i32.gt_u\n                br_if $block3\n                get_local $0\n                i32.const 165\n                i32.add\n                set_local $0\n                br $block2\n              end ;; $block4\n              i64.const 0\n              set_local $5\n              get_local $3\n              i64.const 11\n              i64.le_u\n              br_if $block1\n              br $block\n            end ;; $block3\n            get_local $0\n            i32.const 208\n            i32.add\n            i32.const 0\n            get_local $0\n            i32.const -49\n            i32.add\n            i32.const 255\n            i32.and\n            i32.const 5\n            i32.lt_u\n            select\n            set_local $0\n          end ;; $block2\n          get_local $0\n          i64.extend_u/i32\n          i64.const 56\n          i64.shl\n          i64.const 56\n          i64.shr_s\n          set_local $5\n        end ;; $block1\n        get_local $5\n        i64.const 31\n        i64.and\n        get_local $2\n        i64.const 4294967295\n        i64.and\n        i64.shl\n        set_local $5\n      end ;; $block\n      get_local $1\n      i32.const 1\n      i32.add\n      set_local $1\n      get_local $3\n      i64.const 1\n      i64.add\n      set_local $3\n      get_local $5\n      get_local $4\n      i64.or\n      set_local $4\n      get_local $2\n      i64.const -5\n      i64.add\n      tee_local $2\n      i64.const -6\n      i64.ne\n      br_if $loop\n    end ;; $loop\n    i64.const 0\n    set_local $3\n    get_local $7\n    i64.const 0\n    i64.store offset=24\n    get_local $7\n    get_local $4\n    i64.store offset=16\n    i64.const 59\n    set_local $2\n    i32.const 128\n    set_local $1\n    i64.const 0\n    set_local $4\n    loop $loop1\n      block $block5\n        block $block6\n          block $block7\n            block $block8\n              block $block9\n                get_local $3\n                i64.const 7\n                i64.gt_u\n                br_if $block9\n                get_local $1\n                i32.load8_s\n                tee_local $0\n                i32.const -97\n                i32.add\n                i32.const 255\n                i32.and\n                i32.const 25\n                i32.gt_u\n                br_if $block8\n                get_local $0\n                i32.const 165\n                i32.add\n                set_local $0\n                br $block7\n              end ;; $block9\n              i64.const 0\n              set_local $5\n              get_local $3\n              i64.const 11\n              i64.le_u\n              br_if $block6\n              br $block5\n            end ;; $block8\n            get_local $0\n            i32.const 208\n            i32.add\n            i32.const 0\n            get_local $0\n            i32.const -49\n            i32.add\n            i32.const 255\n            i32.and\n            i32.const 5\n            i32.lt_u\n            select\n            set_local $0\n          end ;; $block7\n          get_local $0\n          i64.extend_u/i32\n          i64.const 56\n          i64.shl\n          i64.const 56\n          i64.shr_s\n          set_local $5\n        end ;; $block6\n        get_local $5\n        i64.const 31\n        i64.and\n        get_local $2\n        i64.const 4294967295\n        i64.and\n        i64.shl\n        set_local $5\n      end ;; $block5\n      get_local $1\n      i32.const 1\n      i32.add\n      set_local $1\n      get_local $3\n      i64.const 1\n      i64.add\n      set_local $3\n      get_local $5\n      get_local $4\n      i64.or\n      set_local $4\n      get_local $2\n      i64.const -5\n      i64.add\n      tee_local $2\n      i64.const -6\n      i64.ne\n      br_if $loop1\n    end ;; $loop1\n    block $block10\n      get_local $4\n      i64.const 5093418677655568384\n      i64.const 3607749778735104000\n      get_local $7\n      i32.const 16\n      i32.add\n      i32.const 16\n      call $load_i64\n      i32.const 16\n      i32.eq\n      br_if $block10\n      i64.const 0\n      set_local $3\n      i64.const 59\n      set_local $2\n      i32.const 128\n      set_local $1\n      i64.const 0\n      set_local $4\n      loop $loop2\n        block $block11\n          block $block12\n            block $block13\n              block $block14\n                block $block15\n                  get_local $3\n                  i64.const 7\n                  i64.gt_u\n                  br_if $block15\n                  get_local $1\n                  i32.load8_s\n                  tee_local $0\n                  i32.const -97\n                  i32.add\n                  i32.const 255\n                  i32.and\n                  i32.const 25\n                  i32.gt_u\n                  br_if $block14\n                  get_local $0\n                  i32.const 165\n                  i32.add\n                  set_local $0\n                  br $block13\n                end ;; $block15\n                i64.const 0\n                set_local $5\n                get_local $3\n                i64.const 11\n                i64.le_u\n                br_if $block12\n                br $block11\n              end ;; $block14\n              get_local $0\n              i32.const 208\n              i32.add\n              i32.const 0\n              get_local $0\n              i32.const -49\n              i32.add\n              i32.const 255\n              i32.and\n              i32.const 5\n              i32.lt_u\n              select\n              set_local $0\n            end ;; $block13\n            get_local $0\n            i64.extend_u/i32\n            i64.const 56\n            i64.shl\n            i64.const 56\n            i64.shr_s\n            set_local $5\n          end ;; $block12\n          get_local $5\n          i64.const 31\n          i64.and\n          get_local $2\n          i64.const 4294967295\n          i64.and\n          i64.shl\n          set_local $5\n        end ;; $block11\n        get_local $1\n        i32.const 1\n        i32.add\n        set_local $1\n        get_local $3\n        i64.const 1\n        i64.add\n        set_local $3\n        get_local $5\n        get_local $4\n        i64.or\n        set_local $4\n        get_local $2\n        i64.const -5\n        i64.add\n        tee_local $2\n        i64.const -6\n        i64.ne\n        br_if $loop2\n      end ;; $loop2\n      i64.const 0\n      set_local $3\n      i64.const 59\n      set_local $2\n      i32.const 16\n      set_local $1\n      i64.const 0\n      set_local $6\n      loop $loop3\n        block $block16\n          block $block17\n            block $block18\n              block $block19\n                block $block20\n                  get_local $3\n                  i64.const 6\n                  i64.gt_u\n                  br_if $block20\n                  get_local $1\n                  i32.load8_s\n                  tee_local $0\n                  i32.const -97\n                  i32.add\n                  i32.const 255\n                  i32.and\n                  i32.const 25\n                  i32.gt_u\n                  br_if $block19\n                  get_local $0\n                  i32.const 165\n                  i32.add\n                  set_local $0\n                  br $block18\n                end ;; $block20\n                i64.const 0\n                set_local $5\n                get_local $3\n                i64.const 11\n                i64.le_u\n                br_if $block17\n                br $block16\n              end ;; $block19\n              get_local $0\n              i32.const 208\n              i32.add\n              i32.const 0\n              get_local $0\n              i32.const -49\n              i32.add\n              i32.const 255\n              i32.and\n              i32.const 5\n              i32.lt_u\n              select\n              set_local $0\n            end ;; $block18\n            get_local $0\n            i64.extend_u/i32\n            i64.const 56\n            i64.shl\n            i64.const 56\n            i64.shr_s\n            set_local $5\n          end ;; $block17\n          get_local $5\n          i64.const 31\n          i64.and\n          get_local $2\n          i64.const 4294967295\n          i64.and\n          i64.shl\n          set_local $5\n        end ;; $block16\n        get_local $1\n        i32.const 1\n        i32.add\n        set_local $1\n        get_local $3\n        i64.const 1\n        i64.add\n        set_local $3\n        get_local $5\n        get_local $6\n        i64.or\n        set_local $6\n        get_local $2\n        i64.const -5\n        i64.add\n        tee_local $2\n        i64.const -6\n        i64.ne\n        br_if $loop3\n      end ;; $loop3\n      get_local $7\n      i64.const 1000000000\n      i64.store offset=8\n      get_local $7\n      get_local $6\n      i64.store\n      get_local $4\n      i64.const 3607749778735104000\n      get_local $7\n      i32.const 16\n      call $store_i64\n      drop\n    end ;; $block10\n    i32.const 0\n    get_local $7\n    i32.const 32\n    i32.add\n    i32.store offset=4\n    )\n  \n  (func $apply\n    (param $0 i64)\n    (param $1 i64)\n    (local $2 i32)\n    (local $3 i32)\n    (local $4 i64)\n    (local $5 i64)\n    (local $6 i64)\n    (local $7 i64)\n    (local $8 i32)\n    i32.const 0\n    i32.const 0\n    i32.load offset=4\n    i32.const 32\n    i32.sub\n    tee_local $8\n    i32.store offset=4\n    i64.const 0\n    set_local $5\n    i64.const 59\n    set_local $4\n    i32.const 128\n    set_local $3\n    i64.const 0\n    set_local $6\n    loop $loop\n      block $block\n        block $block1\n          block $block2\n            block $block3\n              block $block4\n                get_local $5\n                i64.const 7\n                i64.gt_u\n                br_if $block4\n                get_local $3\n                i32.load8_s\n                tee_local $2\n                i32.const -97\n                i32.add\n                i32.const 255\n                i32.and\n                i32.const 25\n                i32.gt_u\n                br_if $block3\n                get_local $2\n                i32.const 165\n                i32.add\n                set_local $2\n                br $block2\n              end ;; $block4\n              i64.const 0\n              set_local $7\n              get_local $5\n              i64.const 11\n              i64.le_u\n              br_if $block1\n              br $block\n            end ;; $block3\n            get_local $2\n            i32.const 208\n            i32.add\n            i32.const 0\n            get_local $2\n            i32.const -49\n            i32.add\n            i32.const 255\n            i32.and\n            i32.const 5\n            i32.lt_u\n            select\n            set_local $2\n          end ;; $block2\n          get_local $2\n          i64.extend_u/i32\n          i64.const 56\n          i64.shl\n          i64.const 56\n          i64.shr_s\n          set_local $7\n        end ;; $block1\n        get_local $7\n        i64.const 31\n        i64.and\n        get_local $4\n        i64.const 4294967295\n        i64.and\n        i64.shl\n        set_local $7\n      end ;; $block\n      get_local $3\n      i32.const 1\n      i32.add\n      set_local $3\n      get_local $5\n      i64.const 1\n      i64.add\n      set_local $5\n      get_local $7\n      get_local $6\n      i64.or\n      set_local $6\n      get_local $4\n      i64.const -5\n      i64.add\n      tee_local $4\n      i64.const -6\n      i64.ne\n      br_if $loop\n    end ;; $loop\n    block $block5\n      get_local $6\n      get_local $0\n      i64.ne\n      br_if $block5\n      i64.const 0\n      set_local $5\n      i64.const 59\n      set_local $4\n      i32.const 144\n      set_local $3\n      i64.const 0\n      set_local $6\n      loop $loop1\n        block $block6\n          block $block7\n            block $block8\n              block $block9\n                block $block10\n                  get_local $5\n                  i64.const 7\n                  i64.gt_u\n                  br_if $block10\n                  get_local $3\n                  i32.load8_s\n                  tee_local $2\n                  i32.const -97\n                  i32.add\n                  i32.const 255\n                  i32.and\n                  i32.const 25\n                  i32.gt_u\n                  br_if $block9\n                  get_local $2\n                  i32.const 165\n                  i32.add\n                  set_local $2\n                  br $block8\n                end ;; $block10\n                i64.const 0\n                set_local $7\n                get_local $5\n                i64.const 11\n                i64.le_u\n                br_if $block7\n                br $block6\n              end ;; $block9\n              get_local $2\n              i32.const 208\n              i32.add\n              i32.const 0\n              get_local $2\n              i32.const -49\n              i32.add\n              i32.const 255\n              i32.and\n              i32.const 5\n              i32.lt_u\n              select\n              set_local $2\n            end ;; $block8\n            get_local $2\n            i64.extend_u/i32\n            i64.const 56\n            i64.shl\n            i64.const 56\n            i64.shr_s\n            set_local $7\n          end ;; $block7\n          get_local $7\n          i64.const 31\n          i64.and\n          get_local $4\n          i64.const 4294967295\n          i64.and\n          i64.shl\n          set_local $7\n        end ;; $block6\n        get_local $3\n        i32.const 1\n        i32.add\n        set_local $3\n        get_local $5\n        i64.const 1\n        i64.add\n        set_local $5\n        get_local $7\n        get_local $6\n        i64.or\n        set_local $6\n        get_local $4\n        i64.const -5\n        i64.add\n        tee_local $4\n        i64.const -6\n        i64.ne\n        br_if $loop1\n      end ;; $loop1\n      get_local $6\n      get_local $1\n      i64.ne\n      br_if $block5\n      get_local $8\n      i64.const 0\n      i64.store offset=24\n      get_local $8\n      i32.const 8\n      i32.add\n      i32.const 24\n      call $read_message\n      i32.const 23\n      i32.gt_u\n      i32.const 160\n      call $assert\n      get_local $8\n      i32.const 8\n      i32.add\n      call $_ZN8currency23apply_currency_transferERKNS_8transferE\n    end ;; $block5\n    i32.const 0\n    get_local $8\n    i32.const 32\n    i32.add\n    i32.store offset=4\n    ))",
    "abi": {
        "types": [
            {
                "new_type_name": "account_name",
                "type": "name"
            }
        ],
        "structs": [
            {
                "name": "transfer",
                "base": "",
                "fields": {
                    "from": "account_name",
                    "to": "account_name",
                    "quantity": "uint64"
                }
            },
            {
                "name": "account",
                "base": "",
                "fields": {
                    "key": "name",
                    "balance": "uint64"
                }
            }
        ],
        "actions": [
            {
                "action_name": "transfer",
                "type": "transfer"
            }
        ],
        "tables": [
            {
                "table_name": "account",
                "index_type": "i64",
                "key_names": [
                    "key"
                ],
                "key_types": [
                    "name"
                ],
                "type": "account"
            }
        ]
    }
}
   */
  def getCode(contractName:String): Future[Option[JsValue]] =
    Client.post(route("get_code"), Json.obj("account_name" -> contractName))

  def getTableRows(scope:String, contractName:String, table:String, lowerBound:Long = 0, upperBound:Long = -1, limit:Long = 10) =
    Client.post(route("get_table_rows"), Json.obj("scope" -> scope,
                                                  "code" -> contractName,
                                                  "table" -> table,
                                                  "json" -> true,
                                                  "lower_bound" -> lowerBound,
                                                  "upper_bound" -> upperBound,
                                                  "limit" -> limit))

  def abiJsonToBin = ???
  def abiBinToJson = ???
  def pushBlock = ???
  def pushTransaction = ???
  def pushTransactions = ???
  def getRequiredKeys = ???

}
