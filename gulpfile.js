var gulp = require('gulp');
var minifyCSS = require('gulp-minify-css')
var concat = require('gulp-concat');
var runSequence = require('run-sequence');
var clean = require('gulp-clean');

var uglify = require('gulp-uglify')
var sourcemaps = require('gulp-sourcemaps');

var src_dir = 'src/main/assets/';
var build_dir = 'build/classes/main/assets/';
function src(rel_path) {
    return src_dir + rel_path;
}

function build(rel_path) {
    return build_dir + rel_path;
}
gulp.task('clean', function () {
    return gulp.src([build_dir], {read: false})
        .pipe(clean({force: true}));
});

gulp.task('vendor-css', function () {
    return gulp.src(src('css/vendor/**/*.css'))
        .pipe(minifyCSS())
        .pipe(concat('vendor.css'))
        .pipe(gulp.dest(build('css')));
});

gulp.task('common-css', function () {
    return gulp.src(src('css/common/**/*.css'))
        .pipe(minifyCSS())
        .pipe(concat('common.css'))
        .pipe(gulp.dest(build('css')));
});
gulp.task('themes-css', function () {
    return gulp.src(src('css/themes/*.css'))
        .pipe(minifyCSS())
        .pipe(gulp.dest(build('css/themes')));
});
gulp.task('oa-css', function () {
    return gulp.src(src('css/oa/*.css'))
        .pipe(minifyCSS())
        .pipe(gulp.dest(build('css/oa')));
});

gulp.task('css', ['vendor-css', 'common-css', 'oa-css', 'themes-css']);
gulp.task('images', function () {
    return gulp.src(src('img/**'))
        .pipe(gulp.dest(build('img')))
});
gulp.task('fonts', function () {
    return gulp.src(src('fonts/**'))
        .pipe(gulp.dest(build('fonts')))
});


gulp.task('common-js', function () {
    return gulp.src(src('js/common/**/*.js'))
        .pipe(sourcemaps.init())
        .pipe(concat('common.js'))
        .pipe(uglify())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(build('js')));
});
gulp.task('vendor-js', function () {
    return gulp.src([
        src('js/vendor/jquery.min.js'),
        src('js/vendor/parsley/parsley.js'),
        src('js/vendor/parsley/parsley.remote.js'),
        src('js/vendor/parsley/zh_cn.js'),
        src('js/vendor/bootstrap.min.js'),
        src('js/vendor/bootstrap-datetimepicker.js'),
        src('js/vendor/bootstrap-datetimepicker.zh-CN.js'),
        src('js/vendor/jquery.bootstrap-growl.js'),
        src('js/vendor/jquery.timers.js'),
        src('js/vendor/jquery.pagination.js')
    ]).pipe(sourcemaps.init())
        .pipe(concat('vendor.js'))
        .pipe(uglify())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(build('js')));
});
gulp.task('oa-js', function () {
    return gulp.src(src('js/oa/*.js'))
        .pipe(sourcemaps.init())
        .pipe(uglify())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(build('js/oa')));
});
gulp.task('js', ['vendor-js', 'common-js', 'oa-js']);

gulp.task('auto', function () {
    gulp.watch(src('**'), function () {
        runSequence('clean', 'images', 'js', 'css', 'fonts');
    });
});

gulp.task('default', ['css', 'images', 'js', 'fonts', 'auto'])