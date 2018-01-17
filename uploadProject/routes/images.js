var express = require('express');
var router = express.Router();
const fs = require('fs');
const ejs = require('ejs');
var multer = require('multer');

var fileName='';

var Storage = multer.diskStorage({
	destination: function(req, file, callback) {
		callback(null, "public/images/");
	},
	filename: function(req, file, callback) {
		callback(null, fileName+".jpg");
	}
});

var upload = multer({
	storage: Storage
}).any(); //Field name and max count

var upload_I = multer({
	storage: Storage
}).array("gimages", 100); //Field name and max count

router.use((req, res, next)=>{
    console.log('Images JS Start...');
    next();
});



router.get('/', (req, res)=> {
    fs.readFile('views/upload.ejs', 'utf8', (err, data) => {
		if(err) {
			res.status(404).send("File is Not Found..");
		}else{
			res.status(200).send(ejs.render(data));
		}
	});
});

router.post('/', (req, res)=> {
  
});

router.get('/show', (req, res)=> {
    let file = req.query.file;
    fs.exists('public/images/'+file+'.jpg', (exists)=>{
        if(exists){
            fs.readFile('public/images/'+file+'.jpg', (err, data)=>{
                if(err){
                    res.end('File Load Error');
                }else{
                    res.end(data);
                }
            });
        }else{
            res.end('file is not Exists');
        }
    });
});

router.post('/show', (req, res)=> {
  
});

router.post('/file', (req, res)=> {
    let file = req.body.file;
    console.log('file Name : ' + file);
    res.status(200).send('OK');
});

router.post('/upload', (req, res)=> {
    let fname = req.query.fname;
    fileName = fname;

    console.log("File Name : " + fname);
    upload_I(req, res, function(err){
        if(err){
            res.status(500).send("NONE");
        }else{
            res.status(200).send("OK");
        }
    });
});



module.exports = router;
