package com.style.me.hd.global.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by Kosh on 23/11/15 12:15 PM
 */
@UiThread public class AnimUtil {

    public interface OnColorAnimated {
        void onColor(int color);
    }

    public static void circularReveal(final View mRevealView, final boolean show) {
        if (ViewCompat.isAttachedToWindow(mRevealView)) {
            if (show) {
                if (mRevealView.isShown()) return;
            } else {
                if (!mRevealView.isShown()) {
                    return;
                }
            }
            reveal(mRevealView, show);
        } else {
            mRevealView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override public boolean onPreDraw() {
                    mRevealView.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (show) {
                        if (mRevealView.isShown()) return true;
                    } else {
                        if (!mRevealView.isShown()) {
                            return true;
                        }
                    }
                    reveal(mRevealView, show);
                    return true;
                }
            });
        }
    }

    public static void circularReveal(final View mRevealView, final boolean fromTop, final boolean show) {
        if (ViewCompat.isAttachedToWindow(mRevealView)) {
            if (show) {
                if (mRevealView.isShown()) return;
            } else {
                if (!mRevealView.isShown()) {
                    return;
                }
            }
            revealFromTop(mRevealView, fromTop, show);
        } else {
            mRevealView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override public boolean onPreDraw() {
                    mRevealView.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (show) {
                        if (mRevealView.isShown()) return true;
                    } else {
                        if (!mRevealView.isShown()) {
                            return true;
                        }
                    }
                    revealFromTop(mRevealView, fromTop, show);
                    return true;
                }
            });
        }
    }

    private static void reveal(final View mRevealView, final boolean show) {
        int cx = (mRevealView.getLeft() + mRevealView.getRight());
        int cy = (mRevealView.getTop() + mRevealView.getBottom());
        int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(600);
            SupportAnimator animator_reverse = animator.reverse();
            if (animator_reverse != null) {
                if (show) {
                    mRevealView.setVisibility(View.VISIBLE);
                    animator.start();
                } else {
                    animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {}

                        @Override
                        public void onAnimationEnd() {
                            mRevealView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel() {}

                        @Override
                        public void onAnimationRepeat() {}
                    });
                    animator_reverse.start();
                }
            }
        } else {
            if (show) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();

            }
        }
    }

    private static void revealFromTop(final View mRevealView, final boolean fromTop, final boolean show) {
        int cx = (mRevealView.getLeft() + mRevealView.getRight());
        int cy = fromTop ? mRevealView.getTop() : mRevealView.getBottom();
        int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(600);
            SupportAnimator animator_reverse = animator.reverse();
            if (animator_reverse != null) {
                if (show) {
                    mRevealView.setVisibility(View.VISIBLE);
                    animator.start();
                } else {
                    animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {}

                        @Override
                        public void onAnimationEnd() {
                            mRevealView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel() {}

                        @Override
                        public void onAnimationRepeat() {}
                    });
                    animator_reverse.start();
                }
            }
        } else {
            if (show) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();

            }
        }
    }

    public static void animateVisibility(final boolean show, @NonNull final View view) {
        view.animate().alpha(show ? 1F : 0F).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (show) view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                super.onAnimationEnd(animation);
                if (!show) view.setVisibility(View.GONE);
            }
        });
    }

    public static void circularRevealFromBottom(final View mRevealView, final View btn, final boolean show) {
        if (ViewCompat.isAttachedToWindow(mRevealView)) {
            if (show) {
                if (mRevealView.isShown()) return;
            } else {
                if (!mRevealView.isShown()) {
                    return;
                }
            }
            fromBottom(mRevealView, btn, show);
        } else {
            mRevealView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override public boolean onPreDraw() {
                    mRevealView.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (show) {
                        if (mRevealView.isShown()) return true;
                    } else {
                        if (!mRevealView.isShown()) {
                            return true;
                        }
                    }
                    fromBottom(mRevealView, btn, show);
                    return true;
                }
            });
        }
    }

    public static void circularRevealFromTop(final View mRevealView, final View btn, final boolean show) {
        if (ViewCompat.isAttachedToWindow(mRevealView)) {
            if (show) {
                if (mRevealView.isShown()) return;
            } else {
                if (!mRevealView.isShown()) {
                    return;
                }
            }
            fromTop(mRevealView, btn, show);
        } else {
            mRevealView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override public boolean onPreDraw() {
                    mRevealView.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (show) {
                        if (mRevealView.isShown()) return true;
                    } else {
                        if (!mRevealView.isShown()) {
                            return true;
                        }
                    }
                    fromTop(mRevealView, btn, show);
                    return true;
                }
            });
        }
    }

    private static void fromTop(final View mRevealView, View btn, final boolean show) {
        int cx = (btn.getLeft() + btn.getRight()) / 2;
        int cy = (mRevealView.getTop() - mRevealView.getBottom());
        int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(1000);
            SupportAnimator animator_reverse = animator.reverse();
            if (animator_reverse != null) {
                if (show) {
                    mRevealView.setVisibility(View.VISIBLE);
                    animator.start();
                } else {
                    animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {}

                        @Override
                        public void onAnimationEnd() {
                            mRevealView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel() {}

                        @Override
                        public void onAnimationRepeat() {}
                    });
                    animator.setDuration(1000);
                    animator_reverse.start();
                }
            }
        } else {
            if (show) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                anim.setDuration(1000);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.setDuration(1000);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();

            }
        }
    }

    private static void fromBottom(final View mRevealView, View btn, final boolean show) {
        int cx = (btn.getLeft() + btn.getRight()) / 2;
        int cy = (mRevealView.getTop() + mRevealView.getBottom());
        int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(600);
            SupportAnimator animator_reverse = animator.reverse();
            if (animator_reverse != null) {
                if (show) {
                    mRevealView.setVisibility(View.VISIBLE);
                    animator.start();
                } else {
                    animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {}

                        @Override
                        public void onAnimationEnd() {
                            mRevealView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel() {}

                        @Override
                        public void onAnimationRepeat() {}
                    });
                    animator_reverse.start();
                }
            }
        } else {
            if (show) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();

            }
        }
    }

    public static void animateTranslateY(final int value, final boolean revertOnFinish, @NonNull final View view) {
        view.animate().translationY(value).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (revertOnFinish) animateTranslateY(0, false, view);
            }
        });
    }

    public static void setTranslationX(View view, float value) {
        view.animate().translationX(value);
    }

    public static Animation scrollingText() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, +1f,
                Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
        );
        animation.setDuration(10000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        return animation;
    }

    public static void blink(final View view) {
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(7);
        animation.setFillAfter(false);
        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);
    }

    public static void setAlpha(View view, float value) {
        view.animate().alpha(value);
    }

    public static void animateColorChange(@NonNull final View view, @ColorInt final int color, @Nullable final OnColorAnimated onColorAnimated) {
        if (!(view.getBackground() instanceof ColorDrawable)) {
            Logger.e("NotInstance");
            view.setBackgroundColor(color);
        }
        animateAppAndStatusBar(view, color);
//        ValueAnimator animator = new ValueAnimator();
//        animator.setIntValues(((ColorDrawable) view.getBackground()).getColor(), color);
//        animator.setEvaluator(new ArgbEvaluator());
//        animator.setDuration(600);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                view.setBackgroundColor((Integer) animation.getAnimatedValue());
//                if (onColorAnimated != null) onColorAnimated.onColor((Integer) animation.getAnimatedValue());
//            }
//        });
//        animator.start();
    }

    public static void animateTextColorChange(@NonNull final TextView textViewx, @ColorInt int color) {
        if (textViewx.getCurrentTextColor() == 0) {
            return;
        }
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(textViewx.getCurrentTextColor(), color);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textViewx.setTextColor((Integer) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private static void animateAppAndStatusBar(final View mRevealView, final int toColor) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRevealView,
                    mRevealView.getWidth() / 2,
                    mRevealView.getHeight() / 2, 0,
                    mRevealView.getWidth() / 2);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(125);
            animator.start();
            mRevealView.setVisibility(View.VISIBLE);
        } else {
            Animator animator = ViewAnimationUtils.createCircularReveal(
                    mRevealView,
                    mRevealView.getWidth() / 2,
                    mRevealView.getHeight() / 2, 0,
                    mRevealView.getWidth() / 2);
            mRevealView.setBackgroundColor(toColor);
            animator.setDuration(125);
            animator.start();
            mRevealView.setVisibility(View.VISIBLE);
        }
    }
}
