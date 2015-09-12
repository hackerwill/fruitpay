/**
 - 使用前請先安裝
	1.nodejs ,官網就有 
	2.gulp相關套件 ,裝了nodejs 就有npm(套件管理工具)可用
 - 開command line ,安裝gulp套件(如果裝不了 可能要用系統管理員權限開啟command line)
    npm install -g gulp gulp-live-server gulp-uglify gulp-header gulp-footer gulp-concat gulp-jshint gulp-cached gulp-remember gulp-minify-html
 - cd 到fruitpay目錄下,設定連結到global的目錄 ,讓gulp在執行時可以引用到lib
    npm link gulp gulp-live-server gulp-uglify gulp-header gulp-footer gulp-concat gulp-jshint gulp-cached gulp-remember --save-dev gulp-minify-html
 - 執行gulp
    gulp
uglify : Minify files 
gulp-live-server : easy light weight server with livereload
header : add header to file(s) in the pipeline
footer : add footer to file(s) in the pipeline.
concat : will concat files by your operating systems 
jshint : A Static Code Analysis Tool for JavaScript
cached : A simple in-memory file cache for gulp
**/
var gulp = require('gulp');
var gls = require('gulp-live-server');
var uglify = require('gulp-uglify'); 
var header = require('gulp-header');
var footer = require('gulp-footer');
var concat = require('gulp-concat');
var jshint = require('gulp-jshint');
var cached = require('gulp-cached');
var minifyHTML  = require('gulp-minify-html');
var remember = require('gulp-remember');	

//要打包的檔案
var config = {
	scriptsGlob : [
		'src/main/webapp/app/app.module.js',	//the top app setup
		'src/main/webapp/app/**/*.module.js', 	//every feature module setup
		'src/main/webapp/app/**/*.js'			//all function
	],
	htmlGlob : [
		'src/main/webapp/app/**/*.html',
		'src/main/webapp/index.html'
	],
	getAllPath : function(){
		return this.scriptsGlob.concat(this.htmlGlob);
	}
};

//打包並存放
gulp.task('wrap', function() {
	return  gulp.src(config.scriptsGlob)
	  .pipe(cached('scripts'))        // only pass through changed files
      .pipe(jshint())                 // do special things to the changed files...
      .pipe(header('(function () {')) // e.g. jshinting ^^^
      .pipe(footer('})();'))          // and some kind of module wrapping
      .pipe(remember('scripts'))      // add back all files to the stream
      .pipe(concat('main.js'))         // do things that require all files
      .pipe(uglify())
      .pipe(gulp.dest('src/main/webapp/build/js'));
});

//將html壓縮放到build對應資料夾
gulp.task('html-minify',function() {
  var opts = {comments:false,spare:false,quotes:true};
  return gulp.src(config.htmlGlob) 
    .pipe(minifyHTML(opts))
    .pipe(gulp.dest('src/main/webapp/build'));
});

gulp.task('watch', function () {
  //檔案變更,就重新打包釋出
  var watcher = gulp.watch(config.scriptsGlob, ['wrap']); // watch the same files in our scripts task 
  var watcher = gulp.watch(config.htmlGlob, ['html-minify']); // watch the same files in our scripts task 
  
  watcher.on('change', function (event) {
   //假如有檔案刪除,要拿掉相對在記憶體catch的檔案資訊
    if (event.type === 'deleted') { // if a file is deleted, forget about it 
      delete cached.caches['scripts'][event.path];
      remember.forget('scripts', event.path);
    }
  });
});

gulp.task('server',function(){
	var server = gls.static('src/main/webapp/build',8888);	//他會自己去找到對應的webapp底下的index.html
	server.start();
	//頁面綁上<script src="//localhost:35729/livereload.js"></script>
	//當檔案變更時可以觸發browser reload
	gulp.watch(config.getAllPath(), function (file) {
		server.notify.apply(server, [file]);
	});
});

gulp.task('default', ['server','watch', 'html-minify', 'wrap']);
