var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');
const { ifError } = require('assert');

//Connection to MySQL
var sqlConnect = mysql.createConnection({
    host    : '127.0.0.1',
    user    : 'root',
    password: 'divya',
    database: 'login',
    port : 3307
});

sqlConnect.connect((err) => {
    if(err) throw err;
    console.log('Connected');
});

var randomString = function(length){
    return crypto.randomBytes(Math.ceil(length/2))
        .toString('hex')
        .slice(0,length);
};
var sha512 = function(password,salt)
{
    var hash = crypto.createHmac('sha512',salt);
    hash.update(password);
    var value = hash.digest('hex');
    return{
        salt : salt,
        passwordHash: value      
    };
};
function checkHashedPassword(userPassword,salt)
{
    var passwordData = sha512(userPassword,salt);
    return passwordData;
}
function saltHashPassword(userPassword){
    var salt = randomString(4);
    var passwordData = sha512(userPassword,salt);
    return passwordData;
}

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

app.post('/register/',(req,res,next)=>{
    var post_data = req.body;
    var uid = uuid.v4();
    var plain_password = post_data.password;
    var hash_data = saltHashPassword(plain_password);
    var password = hash_data.passwordHash;
    var salt = hash_data.salt;
    var name = post_data.name;
    var email = post_data.email;
    
    sqlConnect.query('SELECT * FROM `users` WHERE email =?',[email],function(err,result,fields){
            sqlConnect.on('error',function(err)
            {
                console.log('[MySQL ERROR]',err);
            });
            if(result && result.length)
                res.end(JSON.stringify({"status":"User Already Exists"}));
            else{
                sqlConnect.query('INSERT INTO `users`(`unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES (?,?,?,?,?,NOW(),NOW())',[uid,name,email,password,salt],function(err,result,fields){
                    sqlConnect.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                        res.end(JSON.stringify({"status:":err}));
                    });
                    sqlConnect.query('SELECT `id` FROM `users` WHERE email =?',[email],function(err,result,fields){
                              sqlConnect.on('error',function(err)
                              {
                                  console.log('[MySQL ERROR]',err);
                              });
                    res.send(JSON.stringify(result[0]));
                    })
                })     
            }
        });
})
app.use(bodyParser.json());

bodyParser.urlencoded({
    extended : true
});
app.post('/login/',(req,res,next)=>{
    var post_data = req.body;
    var user_password = post_data.password;
    var email = post_data.email;
    sqlConnect.query('SELECT * FROM `users` WHERE email =?',[email],function(err,result,fields){
        sqlConnect.on('error',function(err)
        {
            console.log('[MySQL ERROR]',err);
        });
        if(result && result.length){
            var salt = result[0].salt;
            var encrypted_password = result[0].encrypted_password;
            var hashed_password = checkHashedPassword(user_password,salt).passwordHash;
            console.log(encrypted_password);
            console.log(hashed_password);
            if(hashed_password==encrypted_password)
            {
                res.end(JSON.stringify(result[0]));
            }else{
                res.end(JSON.stringify('Incorrect Password !'));
            }
        }
        else{ 
            res.json('User does not exists');     
        }
    });
})

app.post('/createUserBal/',(req,res,next)=>{
    var post_data = req.body;
    var id = post_data.id;
    var usershares = post_data.usershares;
    var userbal = post_data.userbal;
    sqlConnect.query('INSERT INTO `user_balance`(`user_id`, `u_shares`, `balance`) VALUES (?,?,?)',[id,usershares,userbal],function(err,result,fields){
        sqlConnect.on('error',function(err)
        {
            console.log('[MySQL ERROR]',err);
        });
        res.send(JSON.stringify({"status":"Success"}));
    })
})

app.get('/result/',(req,res,next)=>{
    sqlConnect.query('SELECT * FROM `users` ',function(err,result,fields){
        sqlConnect.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
        if(result.length>0)
        {
            res.send(JSON.stringify({"status":200,"error":null,"response":result}));
        }
    });
})

app.get('/stocks/',(req,res,next)=>{
    sqlConnect.query('SELECT * FROM `total_shares`',function(err,result,fields){
        sqlConnect.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
        if(result.length>0)
        {
            res.send(JSON.stringify(result));
        }
    })
})

app.get('/userDetails/:id',(req,res)=>{
     sqlConnect.query('SELECT * FROM `user_balance` WHERE user_id = ?',[req.params.id],function(err,result,fields){
         sqlConnect.on('error',function(err){
             console.log('[MySQL ERROR]',err);
         })
         if(result.length>0)
         {
             res.send(JSON.stringify(result));
         }
     })
 })
 app.delete('/stockholderData/:cid/:sid',(req,res,next)=>{
    sqlConnect.query('DELETE FROM `shareholders` WHERE S_ID=? AND C_ID = ?;',[req.params.sid,req.params.cid],function(err,result,fields){
        sqlConnect.on('error',function(err){
            console.log('[MySQL Error]',err);
        })
        res.send(JSON.stringify({"status":"Sell Successful"}));
    })
 })

app.post('/holders/',(req,res,next)=>{
    var post_data = req.body;
    var sid = post_data.sid;
    var sharesname = post_data.sharesname;
    var cid = post_data.cid;
    var cost = post_data.cost;
    var noofshares = post_data.noofshares;
    console.log(sid,sharesname,cid,cost,noofshares);
    sqlConnect.query('INSERT INTO `shareholders`(`S_ID`, `S_NAME`, `C_ID`, `COST_PRICE`, `NOOFSHARES`) VALUES (?,?,?,?,?)',[sid,sharesname,cid,cost,noofshares],function(err,result,fields){
          sqlConnect.on('error',function(err){
          console.log('[MySQL Error]',err);
          });
          res.send(JSON.stringify({"status":"Purchase Successful!"}));
    });
})
app.put('/updateData/',(req,res,next)=>{
    var put_data = req.body;
    var noofshares = put_data.noofshares;
    var cid = put_data.cid;
    console.log(cid,noofshares);
    sqlConnect.query('UPDATE `total_shares` SET `NO_OF_SHARES_AVAIL`=? WHERE C_ID = ?',[noofshares,cid],function(err,result,fields){
        sqlConnect.on('error',function(err){
            console.log('[MySQL Error]',err);
        });      
        res.send(JSON.stringify({"status":"Update Successful!"}))
    })
})
app.put('/updateHolders/',(req,res,next)=>{
    var put_data = req.body;
    var noofshares = put_data.noofshares;
    var sid = put_data.sid;
    var cid = put_data.cid;
    var costprice = put_data.costprice;
    console.log(noofshares,sid,cid);
    sqlConnect.query('UPDATE `shareholders` SET `COST_PRICE`=?,`NOOFSHARES`=? WHERE S_ID = ? AND C_ID = ?',[costprice,noofshares,sid,cid],function(err,result,fields){
        sqlConnect.on('error',function(err){
            console.log('[MySQL Error]',err);
        })
        res.send(JSON.stringify({"status":"Successful"}));
    })
})
app.put('/updateBal/',(req,res,next)=>{
    var put_data = req.body;
    var usershares = put_data.usershares;
    var userbal = put_data.userbal;
    var sid = put_data.sid;
    console.log(usershares,userbal);
    if(userbal<0)
    {
        res.send(JSON.stringify({"status":"You don't have sufficient balance!"}));
    }else{
        sqlConnect.query('UPDATE `user_balance` SET `u_shares`=?,`balance`=? WHERE user_id = ?',[usershares,userbal,sid],function(err,result,fields){
            sqlConnect.on('error',function(err){
                console.log('[MySQL Error]',err);
            });
            res.send(JSON.stringify({"status":"Balance Updated Successfully"}));
        })
    }
})

app.get('/users/:id',(req,res)=>{
    sqlConnect.query('SELECT s.S_ID,s.S_NAME,s.COST_PRICE,s.NOOFSHARES FROM shareholders s,user_balance u WHERE s.C_ID = ? AND s.C_ID = u.user_id',[req.params.id],function(err,result,fields){
        sqlConnect.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        })
        if(result.length>0)
        {
            res.send(JSON.stringify(result));
        }
    })
})

app.listen(3000,()=>{
    console.log('Listening on port 3000');
})